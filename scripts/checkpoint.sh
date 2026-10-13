#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

case "${1:-}" in
  00) tag=demo-00 ;;
  01) tag=demo-01 ;;
  02) tag=demo-02 ;;
  03) tag=demo-03 ;;
  04) tag=demo-04 ;;
  05) tag=demo-05 ;;
  06) tag=demo-final ;;
  *)
    echo "Usage: $0 {00|01|02|03|04|05|06}" >&2
    exit 2
    ;;
esac

echo "Switching to $tag (discarding tracked live-demo edits)..."
git switch --detach --discard-changes "$tag"
echo "Now at $tag"
