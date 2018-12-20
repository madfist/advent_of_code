import sys
import re

def gen(state, rules):
    pots = ['.'] * len(state)
    for i in range(len(state)-4):
        for r in rules:
            if state[i:i+5] == r[0]:
                # print('use', r[0], '->', r[1])
                for p in range(5):
                    if pots[i+p] != '#':
                        pots[i+p] = r[1][p]
                # print('  ', ''.join(pots))
    return ''.join(pots)

def offset(state, offset):
    o = state.find('#')
    if o < 4:
        offset += 4 - o
        state = '.' * (4 - o) + state
    ob = state[::-1].find('#')
    if ob < 4:
        state += '.' * (4 - ob)
    return state, offset

def part1(data, iteration):
    lines = data.split('\n')
    im = re.match(r'initial state:\ ([\.#]+)', lines[0])
    init = ''
    os = 0
    if im:
        init, os = offset(im.group(1), os)

    rules = []
    for line in lines[2:]:
        m = re.match(r'([\.#]+)\ =>\ ([\.#])', line)
        if m:
            rules.append((m.group(1), '..' + m.group(2) + '..'))
    print(10, init)
    # print(rules)
    # print(gen(init, rules))
    for i in range(iteration):
        init = gen(init, rules)
        init, os = offset(init, os)
        print(i+11, init, os)

    sum = 0
    for i,l in enumerate(init):
        if l == '#':
            sum += i-os
    return sum

def part2(data):
    lines = data.split('\n')

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()

    print('part1', part1(data, 20))
    print('part2', part1(data, 100))

if __name__ == '__main__':
    main()
