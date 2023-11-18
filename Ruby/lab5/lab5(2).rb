# Variant 9

def calculate_series_sum(n, x)
  sum = 0.0
  for i in 0..n
    sum += ((x**(4.0 * i + 1)) / (4.0 * i + 1))
  end
  return sum
end

def compute_sums_for_interval(a, b, n, e)
  puts "Calculating for interval [#{a}, #{b}] with n = #{n} and ε = #{e}"
  puts "%-10s%-15s%-15s" % ["X", "Sum for n=#{n}", "Difference"]
  x = a
  while (x <= b)
    sum = calculate_series_sum(n, x)
    puts "%-10s%-15.5f%-15.5f" % [x, sum, (sum - calculate_series_sum(n - 1, x)).abs] unless n.zero?
    x += 0.1
  end
  puts "\n"
end

a = 0.1
b = 0.8
n_range = (17..58)
e = 0.001

n_range.each do |n|
  compute_sums_for_interval(a, b, n, e)
end
