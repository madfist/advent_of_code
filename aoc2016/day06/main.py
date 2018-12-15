import sys

def char_range(c1, c2):
    for c in range(ord(c1), ord(c2)+1):
        yield chr(c)

def get_max_chr(cht, idx):
    max_chr = ''
    max_cnt = 0
    for ch in char_range('a', 'z'):
        if cht[ch][idx] > max_cnt:
            max_chr = ch
            max_cnt = cht[ch][idx]
    return max_chr, max_cnt

def get_min_chr(cht, idx, max_cnt):
    min_chr = ''
    min_cnt = max_cnt
    for ch in char_range('a', 'z'):
        if cht[ch][idx] > 0 and cht[ch][idx] < min_cnt:
            min_chr = ch
            min_cnt = cht[ch][idx]
    return min_chr, min_cnt

def get_message(data):
    lines = data.split('\n')
    msg1 = ['_']*len(lines[0])
    cc1 = [0]*len(lines[0])
    msg2 = ['_']*len(lines[0])
    cc2 = [0]*len(lines[0])
    cht = {}
    for ch in char_range('a', 'z'):
        cht[ch] = [0]*len(lines[0])
    for l in lines:
        idx = 0
        for c in l:
            cht[c][idx] += 1
            msg1[idx], cc1[idx] = get_max_chr(cht,idx)
            msg2[idx], cc2[idx] = get_min_chr(cht,idx,cc1[idx])
            idx += 1
        # print(msg)
    return ''.join(msg1), ''.join(msg2)



def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    print("Message:", get_message(data))

if __name__ == '__main__':
    main()