import sys
import re

def rotate(i,d,s):
    if d == 'right':
        i = -i
    return s[i:] + s[:i]

def rotate_pos_table(n):
    table = [-1]*n
    for i in range(n):
        j = i + 1
        if i >= 4:
            j += 1
        if j >= n:
            j -= n
        if i + j >= n:
            table[i+j-n] = j
        else:
            table[i + j] = j
    return table

def scramble(start, data):
    steps = data.split('\n')
    for s in steps:
        swap_pos_cmd = re.match(r'swap\ position\ (\d+)\ with\ position\ (\d+)', s)
        swap_let_cmd = re.match(r'swap\ letter\ (\S)\ with\ letter\ (\S)', s)
        reverse_cmd  = re.match(r'reverse\ positions\ (\d+)\ through\ (\d+)', s)
        rotate_cmd   = re.match(r'rotate\ (.+?)\ (\d+)\ step[s]*', s)
        rotate_p_cmd = re.match(r'rotate\ based\ on\ position\ of\ letter\ (\S)', s)
        move_pos_cmd = re.match(r'move\ position\ (\d+)\ to\ position\ (\d+)', s)
        if swap_pos_cmd is not None:
            x = start[int(swap_pos_cmd.group(1))]
            y = start[int(swap_pos_cmd.group(2))]
            start = re.sub('#', y, re.sub(y, x, re.sub(x, '#', start)))
        elif swap_let_cmd is not None:
            x = swap_let_cmd.group(1)
            y = swap_let_cmd.group(2)
            start = re.sub('#', y, re.sub(y, x, re.sub(x, '#', start)))
        elif reverse_cmd is not None:
            x = int(reverse_cmd.group(1))
            y = int(reverse_cmd.group(2))
            middle = start[y:x-1:-1]
            if x == 0:
                middle = start[y:x:-1] + start[x]
            start = start[:x] + middle + start[y+1:]
        elif rotate_cmd is not None:
            x = int(rotate_cmd.group(2))
            start = rotate(x, rotate_cmd.group(1), start)
        elif rotate_p_cmd is not None:
            c = rotate_p_cmd.group(1)
            x = start.find(c) + 1
            if x >= 5:
                x += 1
            x = x % len(start)
            start = rotate(x, 'right', start)
        elif move_pos_cmd is not None:
            x = int(move_pos_cmd.group(1))
            y = int(move_pos_cmd.group(2))
            if x < y:
                start = start[:x] + start[x+1:y+1] + start[x] + start[y+1:]
            elif x > y:
                start = start[:y] + start[x] + start[y:x] + start[x+1:]
        else:
            print("Invalid step:", s)
    return start

def unscramble(start, data):
    steps = data.split('\n')
    for s in steps[::-1]:
        swap_pos_cmd = re.match(r'swap\ position\ (\d+)\ with\ position\ (\d+)', s)
        swap_let_cmd = re.match(r'swap\ letter\ (\S)\ with\ letter\ (\S)', s)
        reverse_cmd  = re.match(r'reverse\ positions\ (\d+)\ through\ (\d+)', s)
        rotate_cmd   = re.match(r'rotate\ (.+?)\ (\d+)\ step[s]*', s)
        rotate_p_cmd = re.match(r'rotate\ based\ on\ position\ of\ letter\ (\S)', s)
        move_pos_cmd = re.match(r'move\ position\ (\d+)\ to\ position\ (\d+)', s)
        if swap_pos_cmd is not None:
            x = start[int(swap_pos_cmd.group(1))]
            y = start[int(swap_pos_cmd.group(2))]
            start = re.sub('#', y, re.sub(y, x, re.sub(x, '#', start)))
        elif swap_let_cmd is not None:
            x = swap_let_cmd.group(1)
            y = swap_let_cmd.group(2)
            start = re.sub('#', y, re.sub(y, x, re.sub(x, '#', start)))
        elif reverse_cmd is not None:
            x = int(reverse_cmd.group(1))
            y = int(reverse_cmd.group(2))
            middle = start[y:x-1:-1]
            if x == 0:
                middle = start[y:x:-1] + start[x]
            start = start[:x] + middle + start[y+1:]
        elif rotate_cmd is not None:
            x = int(rotate_cmd.group(2))
            if rotate_cmd.group(1) == 'left':
                x = -x
            start = start[x:] + start[:x]
        elif rotate_p_cmd is not None:
            c = rotate_p_cmd.group(1)
            x = start.find(c)
            r = rotate_pos_table(len(start))[x]
            start = rotate(r, 'left', start)
        elif move_pos_cmd is not None:
            x = int(move_pos_cmd.group(2))
            y = int(move_pos_cmd.group(1))
            if x < y:
                start = start[:x] + start[x+1:y+1] + start[x] + start[y+1:]
            elif x > y:
                start = start[:y] + start[x] + start[y:x] + start[x+1:]
        else:
            print("Invalid step:", s)
    return start

def main():
    if (len(sys.argv) < 4):
        print("Usage python3", sys.argv[0], "<input> enc|dec <start>")
        exit(1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    start = sys.argv[3]
    if sys.argv[2] == 'enc':
        print("Result:", scramble(start, data))
    elif sys.argv[2] == 'dec':
        print("Result:", unscramble(start, data))

if __name__ == '__main__':
    main()