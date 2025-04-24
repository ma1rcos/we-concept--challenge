#!/bin/bash
set -e

export POSTGRES_DB=${POSTGRES_DB:-weconcept}
export POSTGRES_USER=${POSTGRES_USER:-developer}
export POSTGRES_PASSWORD=${POSTGRES_PASSWORD:-1357924680}
export PGDATA=${PGDATA:-/var/lib/postgresql/data/pgdata}

if [ ! -s "$PGDATA/PG_VERSION" ]; then
    gosu postgres initdb
    if [ -f /docker-entrypoint-initdb.d/init.sql ]; then
        gosu postgres postgres --single -jE <<-EOSQL
            CREATE DATABASE $POSTGRES_DB;
EOSQL
        gosu postgres postgres --single -jE <<-EOSQL
            \c $POSTGRES_DB;
            \i /docker-entrypoint-initdb.d/init.sql;
EOSQL
    fi
fi

chmod -R 0700 /var/lib/postgresql/data
chown -R postgres:postgres /var/lib/postgresql/data

exec gosu postgres "$@"