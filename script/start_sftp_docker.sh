##!/usr/bin/env bash
#container="sftp"
#docker rm -f "$sftp"
#docker run -p 22:22 --name "$container" -d atmoz/sftp 9824376278:chandrabrt:::upload
#sleep 3
#docker logs "$container"
docker run -p 22:22 -d atmoz/sftp admin:admin:::upload
