#!/bin/bash
test $(curl 3.36.131.51:8765?a=1\&b=2) -eq 2