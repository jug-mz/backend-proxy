#!/bin/bash

test_file="/opt/backend-proxy/backend-proxy-runner"
new_test_file="/opt/backend-proxy/backend-proxy-runner-new"

if [ -e "$test_file" ]; then
    systemctl daemon-reload
    systemctl stop backend-proxy
    mv "$new_test_file" "$test_file"
    systemctl start backend-proxy
    echo "backend proxy is restarted"
else
    # must be the first deployment
    mv "$new_test_file" "$test_file"
    systemctl enable backend.proxy
    systemctl start backend-proxy
    echo "backend proxy started for the first time"
fi
