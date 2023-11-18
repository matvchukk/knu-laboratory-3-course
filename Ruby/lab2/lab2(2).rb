#Variant 9

def calculateRange(p, t, r)
  range = p**r*(1 - p**(-t))
  return range
end

p = 2
r = 64
t = 16

range = calculateRange(p,t,r)
puts "The range is #{range}"

