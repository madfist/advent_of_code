import sys
import re
import hashlib

class Hasher(object):
    def __init__(self, salt, n=None):
        self.salt = salt
        self.stretches = n
    def __call__(self, i):
        if self.stretches is None:
            return hashlib.md5(bytes(self.salt + str(i), 'utf-8')).hexdigest()
        else:
            s = self.salt + str(i)
            for j in range(self.stretches+1):
                s = hashlib.md5(bytes(s, 'utf-8')).hexdigest()
            return s

def match_n(hashcode, n, char=None):
    hsh = list(hashcode)
    ch = char
    i = 0
    for i in range(len(hsh)-n+1):
        c = 0
        for j in range(i,i+n):
            if char is None:
                ch = hsh[i]
            if hsh[j] != ch:
                break
            else:
                c += 1
        if c == n and ((i+n < len(hsh) and hsh[i+n] != ch) or (i+n == len(hsh))):
            return ch
    return None

def get_keys(hasher, limit):
    key_count = 0
    i = 0
    hashes = []
    for k in range(1001):
        hashes.append(hasher(k))
    while key_count < limit:
        hsh = hashes[0]
        ch = match_n(hsh, 3)
        if ch is not None:
            # print("try", ch, hasher.salt + str(i), hsh)
            for j in range(1, 1000):
                hsh2 = hashes[j]
                if match_n(hsh2, 5, ch) is not None:
                    print("key", ch, hasher.salt + str(i), hsh, "->", hasher.salt + str(i+j), hsh2)
                    key_count += 1
                    break
        i += 1
        del hashes[0]
        hashes.append(hasher(i+1000))
    return i-1

def main():
    if (len(sys.argv) < 3):
        print("Usage: python3", sys.argv[0], "<salt> <n>")
        exit(1)
    hasher1 = Hasher(sys.argv[1])
    print("Index1:", get_keys(hasher1, int(sys.argv[2])))
    hasher2 = Hasher(sys.argv[1], 2016)
    print("Index2:", get_keys(hasher2, int(sys.argv[2])))

if __name__ == '__main__':
    main()
