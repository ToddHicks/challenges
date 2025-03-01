
import json

file = {
    "squadName": "Super hero squad",
    "homeTown": "Metro City",
    "formed": 2016,
    "secretBase": "Super tower",
    "active": True,
    "members": [
      {
        "name": "Molecule Man",
        "age": 29,
        "secretIdentity": "Dan Jukes",
        "powers": ["Radiation resistance", "Turning tiny", "Radiation blast"]
      },
      {
        "name": "Madame Uppercut",
        "age": 39,
        "secretIdentity": "Jane Wilson",
        "powers": [
          "Million tonne punch",
          "Damage resistance",
          "Superhuman reflexes"
        ]
      },
      {
        "name": "Eternal Flame",
        "age": 1000000,
        "secretIdentity": "Unknown",
        "powers": [
          "Immortality",
          "Heat Immunity",
          "Inferno",
          "Teleportation",
          "Interdimensional travel"
        ]
      }
    ]
  }

def search_children(currentpath, childnode):
    if(type(childnode) is dict):
        print(childnode)
        if("name" in childnode):
            names.append(currentpath+'["name"]')
            print("YESSSUM!")
        for n in childnode:
            search_children(currentpath+'["'+n+'"]', childnode[n])
    elif(type(childnode) is list):
        for n in childnode:
            search_children(currentpath+'['+str(childnode.index(n))+']', n)

    else:
        if("name" in str(childnode)):
            print("Name Found!")
            names.append(currentpath)
            print(childnode)

names = []
f = open("data.json")
data = json.load(f)
search_children('', data)       
print("PRINT THE JAZZ")
print(file["members"][2]["name"])   
print(names)


 
def splicer(mystring):
    if(len(mystring)%2==0):
       return 'EVEN'
    else:
       return mystring[0]

names = ['Andy', 'Eve', 'Sally']
mynamemap = map(splicer,names)
mynames = list(mynamemap)
print(mynames)

# Dictionary and path reader
tests = [ 
        {"a": 1},
        {"b": {"c": 1}},
        {"d": 1, "e": 2}
        ]
# Read in a dictionary and print out its path as a tuple returning the output as a list
# {(a): 1}
# {(b,c): 1}
def analyzedict(path,dict1,outdict):
    for node in dict1:
        currentpath =''
        if(path!=currentpath):
            currentpath = path + "/" + node
        else:
            currentpath = node
        if(type(dict1[node]) is dict):
            outdict = analyzedict(currentpath, dict1[node], outdict)
        else:
            outdict[tuple(currentpath.split('/'))]=dict1[node]
    return outdict
outl = {}
outl = analyzedict('',tests[1],outl)
print(outl)

