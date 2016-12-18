import sys
import re

def check(step, taboo, steps):
    if len(steps) > 1:
        print("check", step, steps[-2], not step == steps[-2] and not step in taboo)
        return (not step == steps[-2] and not step in taboo)
    else:
        return not step in taboo

def walk(sx, sy, dx, dy, input):
    if not is_open(sx, sy, input):
        return
    x = sx
    y = sy
    steps = []
    taboo = []
    while x != dx or y != dy:
        if is_open(x+1, y, input) and check((x+1, y), taboo, steps):
            print("right")
            steps.append((x+1, y))
            x += 1
        elif is_open(x, y+1, input) and check((x, y+1), taboo, steps):
            print("down")
            steps.append((x, y+1))
            y += 1
        elif is_open(x-1, y, input) and check((x-1, y), taboo, steps):
            print("left")
            steps.append((x-1, y))
            x -= 1
        elif is_open(x, y-1, input) and check((x, y-1), taboo, steps):
            print("up")
            steps.append((x, y-1))
            y -= 1
        if steps[-1] in steps[:-1]:
            print("deadend", steps[-1])
            taboo.append(steps[steps.index(steps[-1])+1])
            del steps[steps.index(steps[-1])+1:]
            x = steps[-1][0]
            y = steps[-1][1]
        print('-'*50)
        print_plan(50,50,input,steps)
    return len(steps)

def print_plan(sx, sy, input, steps=[]):
    for j in range(sy):
        for i in range(sx):
            if (i,j) in steps:
                print('O',end='')
            elif is_open(i,j,input):
                print(' ',end='')
            else:
                print('#',end='')
        print('')


def is_open(x, y, input):
    z = x*x + 3*x + 2*x*y + y + y*y + input
    n = bin(z).count('1')
    return n % 2 == 0

def main():
    if len(sys.argv) < 4:
        print("Usage: python3", sys.argv[0], "<favnum> <x> <y>")
        exit(1)
    print_plan(40,40,int(sys.argv[1]))
    print("example:", walk(1,1,int(sys.argv[2]),int(sys.argv[3]),int(sys.argv[1])))

if __name__ == '__main__':
    main()
