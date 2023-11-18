# Variant 3

require 'minitest/autorun'

class String
  def numeric?
      Float(self) != nil rescue false
  end
end

def func(a, b, c, x)
  if(a < 0 && c != 0) then
      f = a*(x**2) + b*x + c
  elsif(a > 0 && c == 0) then
      f = (-a)*(x - c)
  else 
      f = a*(x + c)
  end

  if((a.to_i && (b.to_i || c.to_i)) != 0) then
      return f
  else 
      return f.to_i
  end
end

def tabulate_f(a, b, c, x_start, x_end, x_step)
  result = {}
  (x_start..x_end).step(x_step) do |i|
    result[i] = func(a, b, c, i)
  end
  result
end

class TestTabulateFunction < Minitest::Test
  def test_func
    resFirst = tabulate_f(-1, 2, 1, 1, 3, 1)
    resSecond = tabulate_f(1, 2, 1, 1, 3, 1)
    resThird = tabulate_f(1, 2, 0, 1, 3, 1)


    first = {1=>2, 2=>1, 3=>-2}
    second = {1=>2, 2=>3, 3=>4}
    third = {1=>-1, 2=>-2, 3=>-3}
    assert_equal(resSecond, second)
    assert_equal(resFirst, first)
    assert_equal(resThird, third)
  end
end

# puts "Enter a"
# a = gets.chomp.to_f
# puts "Enter b"
# b = gets.chomp.to_f
# puts "Enter c"
# c = gets.chomp.to_f

# puts "Enter start_x"
# xStart = gets.chomp.to_f
# puts "Enter end_x"
# xEnd = gets.chomp.to_f
# puts "Enter step_x"
# xStep = gets.chomp.to_f

# tabulate_f(a, b, c, xStart, xEnd, xStep)

