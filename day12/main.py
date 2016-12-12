import sys
import re

register = {'a': 0, 'b': 0, 'c': 0, 'd': 0}

def run_code(data):
    lines = data.split('\n')
    i = 0
    while i < len(lines):
        args = lines[i].split()
        if args[0] == 'cpy':
            if re.match(r'\d+', args[1]):
                register[args[2]] = int(args[1])
            else:
                register[args[2]] = register[args[1]]
        if args[0] == 'inc':
            register[args[1]] += 1
        if args[0] == 'dec':
            register[args[1]] -= 1
        if args[0] == 'jnz' and (re.match(r'\d+', args[1]) and int(args[1]) > 0 or register[args[1]] is not 0):
            i += int(args[2])
        else:
            i += 1

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    run_code(data)
    print(register)
    register['a'] = 0
    register['b'] = 0
    register['c'] = 1
    register['d'] = 0
    run_code(data)
    print(register)


if __name__ == '__main__':
    main()