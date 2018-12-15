import sys
import hashlib
import re

def step(steps, positions):
    passcode = steps[0]
    pos = positions[0]
    hsh = hashlib.md5(bytes(passcode, 'utf-8')).hexdigest()[:4]
    if re.match(r'[b-f]', hsh[0]) is not None:
        if pos[1] > 0:
            positions.append((pos[0], pos[1]-1))
            steps.append(passcode + 'U')
    if re.match(r'[b-f]', hsh[1]) is not None:
        if pos[1] < 3:
            positions.append((pos[0], pos[1]+1))
            steps.append(passcode + 'D')
    if re.match(r'[b-f]', hsh[2]) is not None:
        if pos[0] > 0:
            positions.append((pos[0]-1, pos[1]))
            steps.append(passcode + 'L')
    if re.match(r'[b-f]', hsh[3]) is not None:
        if pos[0] < 3:
            positions.append((pos[0]+1, pos[1]))
            steps.append(passcode + 'R')
    del steps[0]
    del positions[0]
    return steps, positions

def main():
    if (len(sys.argv) < 2):
        print("Usage: python3", sys.argv[0], "<passcode>")
        exit(1)
    passcode = sys.argv[1]
    positions = [(0,0)]
    steps = [passcode]
    loop = True
    shortest = None
    longest = 0
    while len(steps) > 0:
        steps, positions = step(steps, positions)
        for i in range(-4,0):
            if len(positions) > 3 and positions[i] == (3,3):
                if shortest is None:
                    shortest = re.sub(r'[a-z0-9]', '', steps[i])
                else:
                    longest = len(re.sub(r'[a-z0-9]', '', steps[i]))
                del positions[i]
                del steps[i]
                break
    print("Shortest path:", shortest)
    print("Longest path:", longest)

if __name__ == '__main__':
    main()