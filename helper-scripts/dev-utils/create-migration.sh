#!/usr/bin/env bash

REL_DIR=`dirname $0`
MIGRATION_DIR="src/main/resources/db/migration"

if [ $REL_DIR != "helper-scripts/dev-utils" ]  &&  [ $REL_DIR != "./helper-scripts/dev-utils" ] ; then
  echo This script should be run from the project directory, like: helper-scripts/dev-utils/`basename $0`
  exit 1
fi

function generate_migration_name {
  echo "$MIGRATION_DIR/V`date +%s`__$1.sql"
}

if [ "$#" -ne 1 ]; then
  echo
  echo "Usage: $0 <migration description>"
  echo
  echo "For example,"
  echo "  helper-scripts/dev-utils/$0 add_ship_files"
  echo
  echo "would create the migration:"
  echo "  `generate_migration_name add_ship_files`"
  exit 1
fi

new_migration=`generate_migration_name $1`
touch $new_migration

echo
echo Created migration $new_migration
echo
