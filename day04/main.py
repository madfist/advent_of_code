import sys
import re

def char_range(c1, c2):
    """Generates the characters from `c1` to `c2`, inclusive."""
    for c in range(ord(c1), ord(c2)+1):
        yield chr(c)

def is_real_room(name):
    nodash = re.sub('-', '', name)
    parsed = re.sub(r'(\D*)(\d*)\[(.*)\]', r'\1-\2-\3', nodash)
    tokens = parsed.split('-')
    # print("l: %s -> %s" % (nodash, tokens))
    top5 = [None]*5
    top5v = [0]*5
    for c in char_range('a', 'z'):
        cc = tokens[0].count(c)
        for i in range(5):
            if cc > top5v[i] or (cc == top5v[i] and top5[i] is not None and c < top5[i]):
                # print("%i %r-%r %r-%r" % (i, c, top5[i], cc, top5v[i]))
                for j in range(4,i,-1):
                    top5[j] = top5[j-1]
                    top5v[j] = top5v[j-1]
                top5[i] = c
                top5v[i] = cc
                break
        # print("%r %r %r" % (c, top5, top5v))
    # for i in range(5):
    #     print("%r %r" % (top5[i], top5v[i]))
    return ''.join(top5) == tokens[2], int(tokens[1])

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    lines = data.split("\n")
    summ = 0
    for l in lines:
        r, s = is_real_room(l)
        if r:
            summ += s
    print("Sum: %i" % summ)

if __name__ == '__main__':
    main()