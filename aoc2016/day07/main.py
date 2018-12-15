import sys
import re

def is_abba(chars):
    for i in range(len(chars)-3):
        if chars[i] == chars[i+3] and chars[i+1] == chars[i+2] and chars[i] is not chars[i+1]:
            return True
    return False

def find_babs(chars):
    abas = []
    for i in range(len(chars)-2):
        if chars[i] == chars[i+2] and chars[i] is not chars[i+1]:
            abas.append('' + chars[i+1] + chars[i] + chars[i+1])
    return abas

def count_tls(data):
    lines = data.split('\n')
    count1 = 0
    count2 = 0
    for l in lines:
        l_super = l
        abas = []
        hyper_abba = False
        while re.match(r'.*\[[a-z]*?\].*', l_super) is not None:
            l_hyper = re.sub(r'.*\[([a-z]*?)\].*', r'\1', l_super)
            l_super = re.sub(r'(.*)\[[a-z]*?\](.*)', r'\1 \2', l_super)
            if is_abba(list(l_hyper)):
                hyper_abba = True
            abas.extend(find_babs(list(l_hyper)))

        if is_abba(list(l_super)) and not hyper_abba:
            count1 += 1
        for aba in abas:
            if re.match(r'.*'+aba+r'.*', l_super):
                count2 += 1
                break
    return count1, count2

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    print("TLS,SSL IPs:", count_tls(data))

if __name__ == '__main__':
    main()