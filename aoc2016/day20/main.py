import sys
import re

def prepare(data):
    ranges = []
    for d in data.split('\n'):
        m = re.match(r'(\d+)-(\d+)', d)
        ranges.append([int(m.group(1)), int(m.group(2))])
    return sorted(ranges, key=lambda r:r[0])

def get_lowest(data):
    sr = prepare(data)
    high = sr[0][1]
    for i in range(1,len(sr)):
        if sr[i][0] > high+1:
            return high+1
        high = max(high, sr[i][1])

def count_all(data):
    sr = prepare(data)
    high = sr[0][1]
    count = 0
    for i in range(1,len(sr)):
        if high+1 < sr[i][0]:
            count += sr[i][0] - high - 1
        high = max(sr[i][1], high)
    if high < 4294967295:
        count += 4294967295 - high
    return count

def main():
    if (len(sys.argv) < 2):
        print("Usage: python3", sys.argv[0], "<data>")
        exit(1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    print("Lowest available:", get_lowest(data))
    print("Available addresses:", count_all(data))

if __name__ == '__main__':
    main()