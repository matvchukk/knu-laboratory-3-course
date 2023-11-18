# Variant 9

def compute_first_task
  res = 0
  factorial = 2

  (2..10).each { |i|
    res += ((factorial / (i - 1)) / (factorial * (i + 1))) ** (i * (i + 1))
    factorial *= (i + 1)
  }

  res
end

def compute_second_task(x, a)
  res = 0
  factorial = 1

  (0..10).each{ |i|
    res += ((x.to_f * Math.log(a)) ** i) / factorial
    factorial *= (i + 1)
  }

  res
end

def compute_third_task
  res = 0
  factorials = [1, 1, 1, 1]

  (1..10).each{ |i|
    res += (factorials[0] * factorials[1]) / (factorials[2] * factorials[3])
    factorials[0] *= (3*i) * (3*i + 1) * (3*i + 2)
    factorials[1] *= (i + 2) 
    factorials[2] *= (4 * i + 1) * (4 * i + 2) * (4 * i + 3) * (4 * i + 4)
    factorials[3] *= (2 * i + 1) * (2 * i + 2)
  }
  res
end

x = 2
a = 3
puts "Task 1 => #{compute_first_task.to_s}\n"
puts "Task 2 (a = #{a}, x = #{x}, a**x = #{a**x}) => #{task_2(x, a).to_s}\n"
puts "Task 3 => #{compute_third_task.to_s}\n"
