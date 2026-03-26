import urllib.request
import json
import urllib.error
import sys

base_url = "http://localhost:8080/api"

# 1. Login
def get_token():
    try:
        req = urllib.request.Request(f"{base_url}/auth/login", data=b'{"username":"admin", "password":"password"}', headers={'Content-Type': 'application/json'})
        with urllib.request.urlopen(req) as r:
            return json.loads(r.read())["data"]["token"]
    except:
        req = urllib.request.Request(f"{base_url}/auth/login", data=b'{"username":"admin", "password":"admin123"}', headers={'Content-Type': 'application/json'})
        with urllib.request.urlopen(req) as r:
            return json.loads(r.read())["data"]["token"]

token = get_token()
headers = {"Authorization": f"Bearer {token}"}

endpoints = ["batches", "subjects", "staff", "labs", "days"]
for ep in endpoints:
    try:
        req = urllib.request.Request(f"{base_url}/{ep}", headers=headers)
        with urllib.request.urlopen(req) as r:
            data = json.loads(r.read().decode())["data"]
            print(f"{ep.capitalize()} count: {len(data)}")
    except urllib.error.URLError as e:
        print(f"Error fetching {ep}: {e}")
