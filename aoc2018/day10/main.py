import sys
import re

def step(pos, vel):
    x = pos[0] + vel[0]
    y = pos[1] + vel[1]
    return x,y

def steps(poss, vels):
    for i,s in enumerate(poss):
        poss[i] = step(s, vels[i])
    return poss

def box(poss):
    min_x = min(poss, key=lambda tup: tup[0])[0]
    max_x = max(poss, key=lambda tup: tup[0])[0]
    min_y = min(poss, key=lambda tup: tup[1])[1]
    max_y = max(poss, key=lambda tup: tup[1])[1]
    return min_x, max_x, min_y, max_y

def is_in_box(poss, size_x, size_y):
    nx, xx, ny, xy = box(poss)
    return (xx-nx <= size_x and xy-ny <= size_y)

def print_table(poss):
    nx, xx, ny, xy = box(poss)
    table = [l[:] for l in [['.'] * (xy-ny+1)] * (xx-nx+1)]
    for pos in poss:
        table[pos[0]-nx][pos[1]-ny] = '#'
    for j in range(xy-ny+1):
        for i in range(xx-nx+1):
            print(table[i][j], '', end='')
        print()

def part1(data):
    lines = data.split('\n')
    start = []
    velocity = []
    time = 0
    for line in lines:
        m = re.match(r'position=<\ *([+-]?\d+),\ *([+-]?\d+)>\ velocity=<\ *([+-]?\d+),\ *([+-]?\d+)>', line)
        if m:
            start.append((int(m.group(1)), int(m.group(2))))
            velocity.append((int(m.group(3)), int(m.group(4))))
    while not is_in_box(start, 500, 10):
        time += 1
        start = steps(start, velocity)

    print('part1')
    print_table(start)
    return time

def part2(data):
    lines = data.split('\n')

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()

    print('part2', part1(data))

if __name__ == '__main__':
    main()
