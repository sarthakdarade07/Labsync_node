import urllib.request
import json
import urllib.error
import sys

base_url = "http://localhost:8080/api"

# 1. Login
login_data = json.dumps({"username":"admin", "password":"admin123"}).encode('utf-8')
req = urllib.request.Request(f"{base_url}/auth/login", data=login_data, headers={'Content-Type': 'application/json'})
try:
    with urllib.request.urlopen(req) as response:
        resp = json.loads(response.read().decode())
        token = resp.get("data", {}).get("token")
except urllib.error.URLError:
    # Try alternate password
    login_data = json.dumps({"username":"admin", "password":"password"}).encode('utf-8')
    req = urllib.request.Request(f"{base_url}/auth/login", data=login_data, headers={'Content-Type': 'application/json'})
    with urllib.request.urlopen(req) as response:
        resp = json.loads(response.read().decode())
        token = resp.get("data", {}).get("token")

if not token:
    print("Could not get token!")
    sys.exit(1)

headers = {"Authorization": f"Bearer {token}", "Content-Type": "application/json"}

print("== Adding 5 Labs ==")
for i in range(1, 6):
    lab_data = json.dumps({
        "labName": f"Auto-Lab-{i}",
        "capacity": 30,
        "totalComputers": 30,
        "workingComputers": 30,
        "faultyComputers": 0,
        "osType": "Any",
        "location": "Building A"
    }).encode('utf-8')
    req = urllib.request.Request(f"{base_url}/labs", data=lab_data, headers=headers)
    try:
        urllib.request.urlopen(req)
        print(f"Added Lab {i}")
    except Exception as e:
        print(f"Failed Lab {i}: {e}")

print("\n== Checking Programs and Years ==")
try:
    prog_req = urllib.request.Request(f"{base_url}/programs", headers=headers)
    with urllib.request.urlopen(prog_req) as response:
        programs = json.loads(response.read().decode()).get("data", [])
    
    year_req = urllib.request.Request(f"{base_url}/academic-years", headers=headers)
    with urllib.request.urlopen(year_req) as response:
        years = json.loads(response.read().decode()).get("data", [])

    if programs and years:
        prog_id = programs[0]["id"]
        year_id = years[0]["id"]
        print("== Adding 5 Batches ==")
        for i in range(1, 6):
            batch_data = json.dumps({
                "batchName": f"Auto-Batch-{i}",
                "division": "A",
                "studentCount": 30,
                "semester": "SEM-5",
                "osRequirement": "Any",
                "labsPerWeek": 1,
                "totalHours": 2,
                "programId": prog_id,
                "academicYearId": year_id
                # Intentionally omitting startTime and endTime!
            }).encode('utf-8')
            req = urllib.request.Request(f"{base_url}/batches", data=batch_data, headers=headers)
            try:
                urllib.request.urlopen(req)
                print(f"Added Batch {i}")
            except Exception as e:
                print(f"Failed Batch {i}: {e}")
    else:
        print("No programs or years available to create batches.")
except Exception as e:
    print(f"Error checking programs/years: {e}")

