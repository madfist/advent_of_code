import sys
import re

def validate1(passport):
  params = passport.strip().split(' ')
  if len(params) == 8:
    return True
  elif len(params) == 7:
    for param in params:
      if 'cid' in param:
        return False
    return True
  return False

def validate2(passport):
  if not validate1(passport):
    return False
  params = passport.strip().split(' ')
  validity = True
  for param in params:
    tag = param[:3]
    data = param[4:]
    if tag == 'byr':
      validity = validity and int(data) >= 1920 and int(data) <= 2002
      if not validity:
        print(tag, data, validity)
        return validity
    elif tag == 'iyr':
      validity = validity and int(data) >= 2010 and int(data) <= 2020
      if not validity:
        # print(tag, data, validity)
        return validity
    elif tag == 'eyr':
      validity = validity and int(data) >= 2020 and int(data) <= 2030
      if not validity:
        # print(tag, data, validity)
        return validity
    elif tag == 'ecl':
      validity = validity and data in ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth']
      if not validity:
        # print(tag, data, validity)
        return validity
    elif tag == 'hcl':
      validity = validity and bool(re.match(r'\#[0-9a-f]{6}$', data))
      if not validity:
        # print(tag, data, validity)
        return validity
    elif tag == 'hgt':
      g = re.match(r'(\d+)(cm|in)', data)
      if g and g.group(2) == 'cm':
        validity = validity and int(g.group(1)) >= 150 and int(g.group(1)) <= 193
      elif g and g.group(2) == 'in':
        validity = validity and int(g.group(1)) >= 59 and int(g.group(1)) <= 76
      else:
        validity = False
      if not validity:
        # print(tag, data, validity)
        return validity
    elif tag == 'pid':
      validity = validity and bool(re.match(r'\d{9}$', data))
      print(tag, data, validity)
      if not validity:
        # print(tag, data, validity)
        return validity
  # print()
  return validity

def main():
  if (len(sys.argv) < 2):
    print("Usage python3 %s <input>" % sys.argv[0])
    exit(-1)
  with open(sys.argv[1], 'r') as input:
    data = input.read()
  lines = data.split('\n')
  passports = []
  passport = ""
  for line in lines:
    if (len(line) == 0):
      passports.append(passport)
      passport = ""
    passport += line + ' '

  print(len([p for p in passports if validate1(p)]))
  print(len([p for p in passports if validate2(p)]))

if __name__ == '__main__':
  main()
