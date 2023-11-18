#Variant 9

def implementation_with_if(x)
  if (x <= 0 && x > -4)
    y = ((x - 2).abs / (x**2) * Math.cos(x))**(1.0/7)
  elsif (x > 0 && x <= 12)
    y = (Math.tan(x + 1/(Math.exp(x))) / (Math.sin(x)**2))**(-2.0/7)
  elsif (x < -4 || x > 12)
    y = (1 + x / (1 + x / (1 + x)))**(-1)
  else
    puts "Wrong input"
    return
  end
  return y
end

def implementation_with_case(x)
  case x
  when -3.99..0
    y = ((x-2).abs/(x**2)*Math.cos(x))**(1.0/7)
  when 0..12 
    y = (Math.tan(x + 1/(Math.exp(x)))/(Math.sin(x)**2))**(-2.0/7)
  else 
    y = (1 + x/(1 + x/(1+x)))**(-1)
  end
  return y
end

x = 1
print "Result: #{implementation_with_if(x)}"
print "\nResult: #{implementation_with_case(x)}"