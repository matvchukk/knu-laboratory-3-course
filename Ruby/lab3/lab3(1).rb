#Variant 9
# 1)

a = true
b = true
c = false
x = -20
y = 60
z = 4

expression1 = !(a || b) && (a && !b)
expression2 = (z != y).object_id <= (6 >= y).object_id && (a || b || c) && (x >= 1.5)
expression3 = (8 - x * 2 <= z) && (x**2 != y**2) || (z >= 15)
expression4 = (x > 0 && y < 0) || z >= ((x * y + (-y / x)) + (-z))
expression5 = !(a || (b && !(c || (!a || b))))
expression6 = (x**2 + y**2 >= 1) && x >= 0 && y >= 0
expression7 = (a && ((c && b != b) || a) || c) && b

puts "Task 1)"
puts "#{expression1}"
puts "#{expression2}"
puts "#{expression3}"
puts "#{expression4}"
puts "#{expression5}"
puts "#{expression6}"
puts "#{expression7}"
puts

# 2)

n = 3
m = -6
p = false
q = false

expression = (n / m + m / n > 3) && ((p && q) || (!p && q))

puts "Task 2)"
puts "#{expression}"
puts
