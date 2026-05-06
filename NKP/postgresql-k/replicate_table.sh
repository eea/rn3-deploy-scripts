#!/bin/bash

# Remote database configuration
REMOTE_HOST="your-remote-host.database.azure.com"
REMOTE_PORT="5432"
REMOTE_USER="your_remote_user"
REMOTE_PASSWORD="your_remote_password"
# Local database configuration
LOCAL_HOST="localhost"
LOCAL_PORT="5432"
LOCAL_USER="your_local_user"
LOCAL_PASSWORD="your_local_password"
# Function to copy a database
copy_database() {
    local DB=$1
    echo "Processing database: $DB"
    # Create a timestamp for the backup file
    TIMESTAMP=$(date +%Y%m%d_%H%M%S)
    BACKUP_FILE="${DB}_backup_${TIMESTAMP}.sql"
    echo "Starting database copy process for $DB..."
    # Step 1: Create a dump of the remote database
    echo "Creating dump from remote database..."
    PGPASSWORD=$REMOTE_PASSWORD pg_dump \
        -h $REMOTE_HOST \
        -p $REMOTE_PORT \
        -U $REMOTE_USER \
        -d $DB \
        -F c \
        -f $BACKUP_FILE
    if [ $? -ne 0 ]; then
        echo "Error: Failed to create database dump for $DB"
        return 1
    fi
    # Step 2: Drop the local database if it exists
    echo "Dropping local database if exists..."
    PGPASSWORD=$LOCAL_PASSWORD dropdb \
        -h $LOCAL_HOST \
        -p $LOCAL_PORT \
        -U $LOCAL_USER \
        --if-exists \
        $DB
    # Step 3: Create new local database
    echo "Creating new local database..."
    PGPASSWORD=$LOCAL_PASSWORD createdb \
        -h $LOCAL_HOST \
        -p $LOCAL_PORT \
        -U $LOCAL_USER \
        $DB
    if [ $? -ne 0 ]; then
        echo "Error: Failed to create local database for $DB"
        return 1
    fi
    # Step 4: Restore the dump to the local database
    echo "Restoring database locally..."
    PGPASSWORD=$LOCAL_PASSWORD pg_restore \
        -h $LOCAL_HOST \
        -p $LOCAL_PORT \
        -U $LOCAL_USER \
        -d $DB \
        --no-owner \
        --no-privileges \
        $BACKUP_FILE
    if [ $? -ne 0 ]; then
        echo "Warning: Some errors occurred during restore of $DB (this might be normal)"
    fi
    # Step 5: Cleanup
    echo "Cleaning up temporary files..."
    rm -f $BACKUP_FILE
    echo "Database copy process completed for $DB!"
    echo "----------------------------------------"
}
# Copy each database
copy_database "keycloak"
#copy_database "database2"
echo "All databases have been copied!"