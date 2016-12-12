import sys
import re

bots = {}
outputs = {}

def add_or_continue(dic, key):
    if key not in dic:
        dic[key] = {}

def add_or_append(dic, key, value):
    if key not in dic:
        dic[key] = [value]
    else:
        dic[key].append(value)

def run_bots(data):
    instruction_count = 0
    lines = data.split('\n')
    for l in lines:
        init_cmd = re.match(r'value\ (\d+)\ goes\ to\ bot\ (\d+)', l)
        give_cmd = re.match(r'bot\ (\d+)\ gives\ low\ to\ (bot|output)\ (\d+)\ and\ high\ to\ (bot|output)\ (\d+)', l)
        if init_cmd is not None:
            v = init_cmd.group(1)
            b = init_cmd.group(2)
            add_or_continue(bots, b)
            add_or_append(bots[b], 'values', int(v))

        if give_cmd is not None:
            b = give_cmd.group(1)
            add_or_continue(bots, b)
            add_or_append(bots[b], 'instructions', {'low': give_cmd.group(2) + give_cmd.group(3), 'high': give_cmd.group(4) + give_cmd.group(5)})
            if give_cmd.group(2) == 'bot':
                add_or_continue(bots, give_cmd.group(3))
            else:
                add_or_continue(outputs, give_cmd.group(3))
            if give_cmd.group(4) == 'bot':
                add_or_continue(bots, give_cmd.group(5))
            else:
                add_or_continue(outputs, give_cmd.group(5))
            instruction_count += 1

        to_do = True
        while instruction_count and to_do:
            # print("#",instruction_count)
            to_do = False
            for b in bots:
                # print("b", b)
                if 'values' in bots[b] and len(bots[b]['values']) == 2 and 'instructions' in bots[b]:
                    low = re.match(r'(\D*)(\d+)', bots[b]['instructions'][0]['low'])
                    high = re.match(r'(\D*)(\d+)', bots[b]['instructions'][0]['high'])
                    # print("I", b, min(bots[b]['values']), "->", low.group(0), max(bots[b]['values']), "->", high.group(0))
                    if min(bots[b]['values']) == 17 and max(bots[b]['values']) == 61:
                        print("THE BOT:", b)
                    bl = low.group(2)
                    if low.group(1) == 'bot':
                        add_or_append(bots[bl], 'values', min(bots[b]['values']))
                    else:
                        add_or_append(outputs[bl], 'values', min(bots[b]['values']))
                    bh = high.group(2)
                    if high.group(1) == 'bot':
                        add_or_append(bots[bh], 'values', max(bots[b]['values']))
                    else:
                        add_or_append(outputs[bh], 'values', max(bots[b]['values']))
                    bots[b]['values'] = []
                    del bots[b]['instructions'][0]
                    instruction_count -= 1
                    to_do = True

def get_output():
    return outputs['0']['values'][0] * outputs['1']['values'][0] * outputs['2']['values'][0]

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    run_bots(data)
    # print("BOTS:",bots)
    # print("OUTPUTS:",outputs)
    print("output chips:", get_output())

if __name__ == '__main__':
    main()