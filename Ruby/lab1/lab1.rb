#Variant 9
#require 'Math'

print "Enter x:\n"
x = gets.chomp.to_f  
print "Enter a:\n"
a = gets.chomp.to_f
print "Enter phi:\n"
phi = gets.chomp.to_f

y = ((4.1 * 10**(-1.7))/((x - Math::PI)*Math.sin(5*x))) + (((Math.tan(x.abs)**3 - Math.log10(Math.sqrt(a + phi))))/(Math::E**Math::PI))

puts "Result -> y = #{y}"


  