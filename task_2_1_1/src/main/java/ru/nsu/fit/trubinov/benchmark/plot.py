import collections

import matplotlib.pyplot as plt

parallel = 0
sequential = 0

d = {}
with open("benchmark.txt") as f:
    for line in f:
        if not line.startswith("PrimesBenchmark"):
            continue
        if line.split('.')[1].startswith("Multi"):
            threads = ''
            for c in line.split()[0]:
                if c.isdigit():
                    threads += c
            val = line.split()[3].replace(',', '.')
            d[int(threads)] = float(val)
        if line.split('.')[1].startswith("Parallel"):
            parallel = float(line.split()[3].replace(',', '.'))
        if line.split('.')[1].startswith("Sequential"):
            sequential = float(line.split()[3].replace(',', '.'))

d = collections.OrderedDict(sorted(d.items()))

with open('benchmarkResults.txt', 'w') as out:
    print("Threads Result", file=out)
    for threads, value in d.items():
        print(f'{threads:7d}', f'{value:6.3f}', file=out)
    print(f'\nParallel: {parallel}', file=out)
    print(f'\nSequential: {sequential}', file=out)

axeX = d.keys()
axeY = d.values()

plt.plot(axeX, axeY, 'black')
plt.plot(axeX, axeY, 'ro', label='_nolegend_')
plt.plot([12], [parallel], 'bo')
plt.plot([1], [sequential], 'go')

plt.legend(["Multithreading", "Parallel stream", "Sequential"])

plt.xlabel("Threads")
plt.ylabel("JMH Score (operations/sec)")

plt.savefig('plot.png')
