import sys
import re

def count_viable(data):
    lines = data.split('\n')[2:]
    nodes = []
    for l in lines:
        m = re.match(r'.*?\d+T\s+(\d+)T\s+(\d+)T\s+\d+\%', l)
        nodes.append([int(m.group(2)), int(m.group(1))])
    available = sorted(nodes, key=lambda n:n[0], reverse=True)
    used = sorted(nodes, key=lambda n:n[1])
    pairs = 0
    for a in available:
        u = 0
        i = 0
        while used[i][1] <= a[0]:
            if used[i][1] > 0:
                u += 1
            i += 1
        if a[0] >= a[1] and a[1] != 0:
            u -= 1
        pairs += u
    return pairs

def move_data(data):
    lines = data.split('\n')[2:]
    nodes = []
    for l in lines:
        m = re.match(r'.*?x(\d+)-y(\d+)\s+\d+T\s+(\d+)T\s+(\d+)T\s+\d+\%', l)
        x = int(m.group(1))
        y = int(m.group(2))
        if y == 0:
            nodes.append([])
        nodes[x].append([int(m.group(4)), int(m.group(3))])
    print_nodes(nodes)
    return 0

def print_nodes(nodes):
    for j in range(len(nodes[0])):
        for i in range(len(nodes)):
            if nodes[i][j][1] > 0 and nodes[i][j][1] <= 89:
                print('o', end='')
            elif nodes[i][j][1] > 89:
                print('#', end='')
            elif nodes[i][j][1] == 0:
                print('_', end='')
        print('|', repr(j).ljust(3))

def main():
    if (len(sys.argv) < 2):
        print("Usage python3", sys.argv[0], "<input>")
        exit(1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    print("Viable pairs:", count_viable(data))
    print("Steps to move:", move_data(data))

if __name__ == '__main__':
    main()