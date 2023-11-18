# Variant 9

def prm(a, b, n, f)
  integral = 0.0
  h = (b - a).fdiv(n)
  a = a.to_f
  for i in 1..n
      xi = a + i*h - h/2.0
      integral += f.call(xi)
  end
  return integral * h
end

def tra(a, b, n, f)
  a_ = a.to_f
  b_ = b.to_f
  h = (b_ - a_).fdiv(n)
  n-= 1
  sum = 0.0
  for i in 1..n
      xi = a_ + i*h
      sum += f.call(xi)
  end
  integral = (h / 2.0)*(f.call(a_) + 2*sum + f.call(b_))
  return integral
end

f1 = Proc.new{|x| (1/(1 + x**(1/2)))}
a1 = 0.2
b1 = 2.1

f2 = Proc.new{|x| (1/(5 - 3 * Math.cos(x)))}
a2 = 0.2
b2 = 3.0/(2.0 * Math::PI)

n = 10
print "Integral f1 (PRM) = ", prm(a1, b1, n, f1), "\n"
print "Integral f1 (TRA) = ", tra(a1, b1, n, f1), "\n\n"

print "Integral f2 (PRM) = ", prm(a2, b2, n, f2), "\n"
print "Integral f2 (TRA) = ", tra(a2, b2, n, f2), "\n\n"