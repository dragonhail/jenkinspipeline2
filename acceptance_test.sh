#!/bin/bash
test $(curl http://3.36.131.51:8765?a=1\&b=2) -eq 2