import sys
import re

def build_graph(data, backward = True):
  lines = data.split('\n')
  bags = []
  for line in lines:
    m = re.match(r'^(\S+ \S+) bags contain (.*)$', line)
    new_bag = m.group(1)
    rest = m.group(2)
    m_bag = re.match(r'^(\d+) (\S+ \S+) bags?([.,]) ?(.*)$', rest)
    m_nobag = re.match(r'no other bags\.', rest)
    if m_nobag:
      continue
    while m_bag:
      bag_no = int(m_bag.group(1))
      bag = m_bag.group(2)
      end = m_bag.group(3)
      rest = m_bag.group(4)
      # print('=> n', bag_no, 'b', bag, '$', end, '#', rest)
      try:
        if backward:
          the_bag = next(b for b in bags if b[0] == bag)
          the_bag[1].append((new_bag, bag_no))
        else:
          the_bag = next(b for b in bags if b[0] == new_bag)
          the_bag[1].append((bag, bag_no))
      except StopIteration as e:
        if backward:
          bags.append((bag, [(new_bag, bag_no)]))
        else:
          bags.append((new_bag, [(bag, bag_no)]))
      if end == '.':
        # print('==> break')
        break
      m_bag = re.match(r'^(\d+) (\S+ \S+) bags?([.,]) ?(.*)$', rest)
      # print('==> match', m_bag)
  return bags

def count_all_distinct_parents(bags, bag, visited_bags):
  # print('count_all_distinct_parents', bag, visited_bags)
  try:
    bag_node = next(b for b in bags if b[0] == bag)
    counter = 0
    for sub_bag in bag_node[1]:
      up_result = count_all_distinct_parents(bags, sub_bag[0], visited_bags)
      if not up_result[1] in visited_bags:
        visited_bags.add(up_result[1])
        counter += up_result[0]
    # print('=>', counter, bag)
    return counter + 1, bag
  except StopIteration as e:
    # print('=> 1', bag)
    return 1, bag

def sum_all_children(bags, bag, num):
  try:
    bag_node = next(b for b in bags if b[0] == bag)
    counter = 0
    for sub_bag in bag_node[1]:
      counter += num * sum_all_children(bags, sub_bag[0], sub_bag[1])
    # print('=>', bag, counter)
    return num + counter
  except StopIteration as e:
    # print('=>', bag, num)
    return num;

def task1(data, bag):
  counter = 0
  bags = build_graph(data)
  # print(bags)
  return count_all_distinct_parents(bags, bag, set())[0] - 1

# sum all children
def task2(data, bag):
  counter = 0
  bags = build_graph(data, False)
  # print(bags)
  return sum_all_children(bags, bag, 1) - 1

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()

    print(task1(data, 'shiny gold'))
    print(task2(data, 'shiny gold'))

if __name__ == '__main__':
    main()
