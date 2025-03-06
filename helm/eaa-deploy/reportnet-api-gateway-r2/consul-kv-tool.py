#!/usr/bin/env python3
"""
Import/Export tool for Consul Key-Value storage.

If your server has ACL enabled,
be sure to export CONSUL_HTTP_TOKEN env variable or use -t to provide a token

Usage:

  Export KV storage decoding values
  ./consul-kv-tool.py export > file.json

  Export a certain path, decoding values
  ./consul-kv-tool.py export --path "vault/staging" > file.json

  Dump KV storage "as is", with base64 encoded values
  ./consul-kv-tool.py dump > file.json

  Decode base64 in existing dump/backup:
  ./consul-kv-tool.py decode-dump -f file.json

  Import KV from file
  ./consul-kv-tool.py import -f file.json

  Import some part of a file
  ./consul-kv-tool.py import -f file.json --path "vault/staging"

"""


import json
import requests
import base64
import sys
import argparse
import os


def download($server, token, consul_path=""):

    url = server + "/v1/kv/" + consul_path
    params = {"token": token, "recurse": 1}

    try:
        response = requests.get(url, params=params)
        if response.status_code != 200:
            raise Exception("HTTP code %s on GET %s" % (response.status_code, url))

        return response.json()

    except Exception as e:
        print("ERROR during download:", e, file=sys.stderr)
        exit(1)


def process(downloaded_data):

    #
    # Some typical kv data probems are fixed here:
    #
    # - sometimes a 'directory' appears as a key with trailing "/" - we should just skip these keys,
    #   since 'directory' will be created once we put the keys in it.
    #
    # - sometimes a non-ascii chars like "eé" appear in values instrad of Null.
    #   These values are replaced with None.
    #

    result_obj = []

    for obj in downloaded_data:
        try:
            # Skip 'directory creation' keys
            if obj['Key'][-1] == "/":
                    continue

            # Decode only non-empty values
            if obj['Value']:
                    obj['Value'] = base64.b64decode(obj['Value'])
                    obj['Value'] = str(obj['Value'].decode('utf-8'))

            result_obj.append(obj)

        # Replace values with bad chars with None (aka null) and continue
        except UnicodeDecodeError as e:
            print("WARNING: bad chars in", obj['Key'], ": ", e, file=sys.stderr)
            obj['Value'] = None
            result_obj.append(obj)

        except Exception as e:
            print ("ERROR during processing:", e, file=sys.stderr)
            exit(1)

    return result_obj


def read_from_file(file_path, consul_path=None):

    try:
        f = open(file_path, "r")
        data = json.load(f)
        f.close()

        if consul_path:
            result_obj = []
            for obj in data:
                if consul_path in obj['Key']:
                    result_obj.append(obj)
            data = result_obj

    except Exception as e:
        print ("ERROR reading file:", e, file=sys.stderr)
        exit(1)

    return data


def output(data):
    print(json.dumps(data, sort_keys=True, indent=4, separators=(',', ': ')))


def upload(server, token, data):

    params = {"token": token}

    for obj in data:
        try:
            payload = obj['Value']
            url = server + "/v1/kv/" + obj['Key']

            response = requests.put(url, data=payload, params=params)
            if response.status_code != 200:
                raise Exception("HTTP code %s on PUT %s" % (response.status_code, url))

        except Exception as e:
            print ("ERROR uploading:", e, file=sys.stderr)
            exit(1)


if __name__ == "__main__":

    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("action", type=str, choices=["export", "import", "dump", "decode-dump"], action="store", help="export or import")
    parser.add_argument("--file", "-f", type=str, help="json file to read", metavar='')
    parser.add_argument("--path", "-p", type=str, help="Consul kv path: 'vault/master/something/key'", default="", metavar='')
    parser.add_argument("--server", "-s", type=str, help="Consul server, proto://url:port", default="http://localhost:8500", metavar='')
    parser.add_argument("--token", "-t", type=str, help="Consul ACL token", metavar='')
    args = parser.parse_args()

    if args.token:
        token = args.token
    elif 'CONSUL_HTTP_TOKEN' in os.environ:
        token = os.environ['CONSUL_HTTP_TOKEN']
    else:
        print("Warning: no Consul token provided.", file=sys.stderr)
        

    if args.action in ['import', 'decode-dump'] and not args.file:
        print ("Provide a filename using -f parameter.", file=sys.stderr)
        exit(1)

    if args.action == 'export':
        data = download(args.server,  args.path)
        data = process(data)
        output(data)

    elif args.action == 'import':
        data = read_from_file(args.file, args.path)
        upload(args.server, token, data)

    elif args.action == 'dump':
        data = download(args.server, token, args.path)
        output(data)

    elif args.action == 'decode-dump':
        data = read_from_file(args.file, args.path)
        data = process(data)
        output(data)
