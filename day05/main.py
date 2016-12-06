import sys
import hashlib
import re

def get_passwords(door_id):
    pw1 = ""
    pw2 = ['_']*8
    c = 0
    while len(pw1) < 8 or pw2.count('_') > 0:
        d = door_id + '%i'%c
        hsh = hashlib.md5(bytes(d, 'utf-8')).hexdigest()
        if re.match(r'^00000.*', hsh):
            if len(pw1) < 8:
                pw1 += hsh[5]
            pos = ord(hsh[5])-ord('0')
            if 0 <= pos and pos < 8 and pw2[pos] == '_':
                pw2[pos] = hsh[6]
                print(pw2)
        c += 1
    return pw1, ''.join(pw2)

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    print("Passwords:", get_passwords(data))

if __name__ == '__main__':
    main()