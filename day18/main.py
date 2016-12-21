import sys

def find_traps(row):
    extended = '.' + row + '.'
    new_row = ''
    for i in range(len(row)):
        x = extended[i:i+3]
        if x == '.^^' or x == '^^.' or x == '..^' or x == '^..':
            new_row += '^'
        else:
            new_row += '.'
    return new_row

def main():
    if (len(sys.argv) < 3):
        print("Usage: python3", sys.argv[0], "<first_row> <n_rows>")
        exit(1)
    with open(sys.argv[1], 'r') as input:
        first_row = input.read()
    safes = first_row.count('.')
    # print(first_row)
    row = first_row
    for i in range(int(sys.argv[2])-1):
        row = find_traps(row)
        safes += row.count('.')
        # print(row)
    print("Safe tiles:", safes)

if __name__ == '__main__':
    main()