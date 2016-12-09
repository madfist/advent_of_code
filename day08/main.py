import sys
import re

screen = [['_' for i in range(50)] for j in range(6)]

def count_pixels(data):
    lines = data.split('\n')
    for l in lines:
        rect_cmd = re.match(r'rect\ (\d+)x(\d+)', l)
        rotate_row_cmd = re.match(r'rotate\ row\ y=(\d+)\ by\ (\d+)', l)
        rotate_col_cmd = re.match(r'rotate\ column\ x=(\d+)\ by\ (\d+)', l)
        if rect_cmd is not None:
            for i in range(int(rect_cmd.group(1))):
                for j in range(int(rect_cmd.group(2))):
                    screen[j][i] = '#'
        if rotate_row_cmd is not None:
            j = int(rotate_row_cmd.group(1))
            for n in range(int(rotate_row_cmd.group(2))):
                tmp = screen[j][49]
                for i in range(49,0,-1):
                    screen[j][i] = screen[j][i-1]
                screen[j][0] = tmp
        if rotate_col_cmd is not None:
            i = int(rotate_col_cmd.group(1))
            for n in range(int(rotate_col_cmd.group(2))):
                tmp = screen[5][i]
                for j in range(5,0,-1):
                    screen[j][i] = screen[j-1][i]
                screen[0][i] = tmp
    summ = 0
    for i in range(50):
        for j in range(6):
            if screen[j][i] == '#':
                summ += 1
    return summ

def print_screen():
    for j in range(6):
        print(''.join(screen[j]))

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    print("Pixel count:", count_pixels(data))
    print_screen()

if __name__ == '__main__':
    main()