echo "Getting VM parameters..."
$sshKey = (doctl compute ssh-key list -o json | ConvertFrom-Json | where {$_.name.Contains('dummy')}).id
$imageId = (doctl compute image list -o json | ConvertFrom-Json | where {$_.name.Contains('Talos')}).id
$timestamp = Get-Date -Format "yyyyMMdd-HHmmss"
$vmName = "kcert-test-$timestamp"
mkdir $vmName | Out-Null
echo "Creating droplet..."
$vmJson = (doctl compute droplet create --region sfo3 --image $imageId --size s-2vcpu-4gb --enable-private-networking --ssh-keys $sshKey $vmName --wait -o json)
$vm = $vmJson | ConvertFrom-Json
$vmIp = $vm[0].networks.v4 | where {$_.type -eq 'public'} | Select-Object -ExpandProperty ip_address
echo "VM created with IP address: $vmIp"
echo $vmIp > $vmName/ip.txt
echo "Initializing Talos cluster at $vmIp"
talosctl gen config $vmName "https://${vmIp}:6443" --additional-sans $vmIp -o $vmName
$env:TALOSCONFIG = (Resolve-Path "$vmName/talosconfig").Path
talosctl config endpoint $vmIp
talosctl config node $vmIp
$yaml = Get-Content -Path "${vmName}/controlplane.yaml"
$yaml = $yaml -replace '# allowSchedulingOnControlPlanes:', 'allowSchedulingOnControlPlanes:'
Set-Content -Path "${vmName}/controlplane.yaml" -Value $yaml
talosctl apply-config --insecure --nodes $vmIp --file "${vmName}/controlplane.yaml"
echo "Sleeping for 10 seconds to allow the node to initialize..."
Start-Sleep -Seconds 10
talosctl bootstrap
echo "Sleeping for 10 seconds to allow the cluster to stabilize..."
Start-Sleep -Seconds 10
talosctl health
talosctl kubeconfig $vmName
$env:KUBECONFIG = (Resolve-Path "$vmName/kubeconfig").Path
echo "Setting up MetalLB"
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.15.2/config/manifests/metallb-native.yaml
kubectl wait --timeout=5m --for=condition=available --all deployments -n metallb-system
@"
apiVersion: metallb.io/v1beta1
kind: IPAddressPool
metadata:
  name: lab-pool
  namespace: metallb-system
spec:
  addresses:
  - $vmIp/32
"@ | kubectl apply -f -
echo "Setting up Envoy"
helm install eg oci://docker.io/envoyproxy/gateway-helm --version v1.5.1 -n envoy-gateway-system --create-namespace
kubectl wait --timeout=5m -n envoy-gateway-system deployment/envoy-gateway --for=condition=Available
echo "Here are your environment variables:"
$envVars = @(
    "`$env:KUBECONFIG = '$env:KUBECONFIG'",
    "`$env:TALOSCONFIG = '$env:TALOSCONFIG'",
    "`$env:VMIP = '$vmIp'"
)
$envVars | ForEach-Object { echo $_ }
$envVars | Out-File -FilePath "$vmName/env.txt" -Encoding utf8