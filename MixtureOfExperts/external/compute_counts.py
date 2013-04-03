instances = set()
labels = set()
experts = set()

fname = '../data/omar_data'
with open(fname, 'r') as f:
    for line in f:
        comps = line.split()
        instances.add(comps[0])
        num_labels = (len(comps) - 1) / 2
        for i in range(1, num_labels + 1):
            experts.add(comps[i])
            labels.add(comps[num_labels + i])


print 'instances', len(instances)
print 'experts', len(experts)
print 'labels', len(labels)

