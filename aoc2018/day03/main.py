import sys
import re

def parse(line):
    m = re.match(r'#(\d+)\ @\ (\d+),(\d+):\ (\d+)x(\d+)', line);
    return (int(m.group(1)), int(m.group(2)), int(m.group(3)), int(m.group(4)), int(m.group(5)))

def fill(dim, fabric):
    for x in range(dim[1], dim[1] + dim[3]):
        for y in range(dim[2], dim[2] + dim[4]):
            fabric[y*1000+x] += 1

def check(dim, fabric):
    ret = True
    for x in range(dim[1], dim[1] + dim[3]):
        for y in range(dim[2], dim[2] + dim[4]):
            # print(x,y,fabric[y*1000+x])
            ret = ret and (fabric[y*1000+x] == 1)
    return ret

def part12(data):
    lines = data.split('\n')
    fabric = [0] * (1000 * 1000)
    for line in lines:
        if len(line) < 1:
            continue
        fill(parse(line), fabric)
    sum = 0
    for i in fabric:
        if i > 1:
            sum += 1
    print('part1', sum)

    for line in lines:
        if len(line) < 1:
            continue
        if check(parse(line), fabric):
            print('part2', parse(line)[0])

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()

    part12(data)

if __name__ == '__main__':
    main()
