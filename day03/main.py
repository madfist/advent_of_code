import sys

def is_triangle(line):
    sides = line.split()
    sn = []
    for s in sides:
        sn.append(int(s))
    return sn[0] + sn[1] > sn[2] and sn[0] + sn[2] > sn[1] and sn[1] + sn[2] > sn[0]

def check_horizontaly(data):
    lines = data.split("\n")
    count = 0
    for l in lines:
        if is_triangle(l):
            # print(l)
            count += 1
    print("Triangles: %i" % count)

def check_vertically(data):
    lines = data.split("\n")
    lines.append("")
    count = 0
    line_count = 0
    l3 = []
    for l in lines:
        if line_count == 3:
            line_count = 0
            c = [[1 for a in range(3)] for b in range(3)]
            for i in range(3):
                for j in range(3):
                    c[j][i] = l3[i].split()[j]
            l3 = []
            for i in range(3):
                if is_triangle(' '.join(c[i])):
                    count += 1
                # else:
                #     print("not triangle: %r" % c[i])


        l3.append(l)
        line_count += 1
    print("Triangles: %i" % count)


def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    check_horizontaly(data)
    check_vertically(data)

if __name__ == '__main__':
    main()