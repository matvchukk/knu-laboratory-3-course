# Variant 9

def y(x)
  begin
    return (x-c/(x**(3/4)+x**(1/n)*c**(1/4)))*((x**(1/2)*c**(1/4)+n**(1/4))/(x**(1/2)+c**(1/2)))*((x**(1/4)*c**(-1/4))/(x**(1/2)-2*x**(1/4)*c**(1/4)+c**(1/n)))
  rescue
    return 0
  end
end

def z(x, pi)
  (Math.sin(2 * x) ** 2 - Math.cos((pi / 3) - 2 * x) * Math.sin(-(pi / 6) - 2 * x ) - 1/Math.tan((pi + x)/(x + 1)) ** (2/x))
end

def y_tab(pi, n, c)
  res = {}
  (1..n).step((n - 1).to_f / (n + c)) do |i|
    res[i] = y(i)
  end
  res
end

def z_tab(pi, n, c)
  res = {}
  i = pi / n
  delta = (pi - pi / n) / ((3.0/2)*n + c)
  while i <= pi do
    res[i] = z(i, pi)
    i += delta
  end

  res
end

def y_z_tab(pi, n, c)
  res = {}
  (2..c).step((c - 2).to_f / (2*n)) do |j|
    if j > 2 && j < n
      res[j] = y(j)
    elsif j > n && j < 2*n
      res[j] = z(j, pi)
    else
      res[j] = y(j) + z(j, pi)
    end
  end

  res
end

def print_result(dict)
  dict.each { |i|
    print i.to_s + ": " + dict[i].to_s + "\n"
  }

  print "\n"
end

pi = 3.141
n = 10
c = 30

print "Subtask y tabulation\n"
print_result(y_tab(pi, n, c))
print "Subtask z tabulation\n"
print_result(z_tab(pi, n, c))
print "Subtask y z tabulation\n"
print_result(y_z_tab(pi, n, c))