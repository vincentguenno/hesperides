#!/bin/sh
set -o pipefail -o errexit -o nounset

if [ -z "${PROJECTION_REPOSITORY_MONGO_URI:-}" ]; then
    if ! echo "${SPRING_PROFILES_ACTIVE:-}" | grep -qF fake_mongo; then
        echo 'Either $PROJECTION_REPOSITORY_MONGO_URI must be defined or $SPRING_PROFILES_ACTIVE must contain "fake_mongo"' >&2
        exit 1
    fi
else
    create_ok=$(mongo $PROJECTION_REPOSITORY_MONGO_URI --quiet --eval "db.createCollection('test').ok")
    if [ $create_ok -ne 1 ]; then
        echo -e 'Not enough permissions to create a collection: connected to a non-PRIMARY node ?\nCheck that $PROJECTION_REPOSITORY_MONGO_URI contains replicaSet=...' >&2
        exit 1
    fi

    mongo $PROJECTION_REPOSITORY_MONGO_URI mongo_create_collections.js
fi

# If args were passed to this script, execute them as a command, else do nothing:
exec "${@:-true}"