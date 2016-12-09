import sys
import re

def decompress(data):
    decompressed = ""
    d = ''.join(data.split('\n'))
    while len(d):
        s = re.match(r'^.*?(\((\d+?)x(\d+?)\))(.*)', d)
        if s is None:
            decompressed += d
            break
        # print(s.groups())
        dec, marker, d = d.partition(s.group(1))
        decompressed += dec
        for i in range(int(s.group(3))):
            decompressed += d[:int(s.group(2))]
        d = d[int(s.group(2)):]
    # print("dec:", decompressed)
    return decompressed

def dec(d):
    print("dec", d)
    length = 0
    while len(d):
        s = re.match(r'^.*?(\((\d+?)x(\d+?)\))(.*)', d)
        if s is None:
            print("end", d, len(d))
            return length + len(d)
        # print(d, s.groups())
        decomp, marker, d = d.partition(s.group(1))
        length += int(s.group(3)) * dec(d[:int(s.group(2))])
        d = d[int(s.group(2)):]
    print("L",length)
    return len(decomp) + length


def full_decompress(data):
    length = 0
    d = ''.join(data.split('\n'))
    return dec(d)

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    print("Char count:", len(decompress(data)))
    print("Char count:", full_decompress(data))

if __name__ == '__main__':
    main()