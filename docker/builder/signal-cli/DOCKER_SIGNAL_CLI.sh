#!/bin/bash
docker run --name signal -it --rm -v "`pwd`/data:/root/.local" signal-cli "$@"
