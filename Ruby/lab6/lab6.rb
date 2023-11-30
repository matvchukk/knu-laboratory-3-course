require 'test/unit'

class TestStudent < Test::Unit::TestCase
  def setup
    @students_array = [
      Student.new('Matviichuk', 'IPS-13', 5, 5, 5),
      Student.new('Petryk', 'IPS-13', 5, 5, 5),
      Student.new('Sidorov', 'IPS-11', 2, 2, 3),
      Student.new('Smirnov', 'K-15', 3, 4, 5),
      Student.new('Shalko', 'K-12', 4, 5, 5),
      Student.new('Petrov', 'K-19', 4, 4, 5),
      Student.new('Pysarenko', 'IPS-11', 5, 3, 2),
      Student.new('Avramenko', 'IPS-12', 4, 3, 4),
      Student.new('Kotik', 'K-12', 3, 4, 3),
      Student.new('Tishchenko', 'IPS-12', 5, 5, 5),
      Student.new('Mylostian', 'K-13', 4, 2, 4),
      Student.new('Bazhyn', 'K-19', 3, 5, 3)
    ]
  end

  def test_display_students_with_arrears
    result = display_students_with_arrears(@students_array)
    assert_equal %w[Sidorov Pysarenko Mylostian], result
  end

  def test_quality_of_performance
    result = quality_of_performance(@students_array)
    expected_averages = {
      'Matviichuk' => 5.0,
      'Petryk' => 5.0,
      'Sidorov' => 2.3333333333333335,
      'Smirnov' => 4.0,
      'Shalko' => 4.666666666666667,
      'Petrov' => 4.333333333333333,
      'Pysarenko' => 3.3333333333333335,
      'Avramenko' => 3.6666666666666665,
      'Kotik' => 3.3333333333333335,
      'Tishchenko' => 5.0,
      'Mylostian' => 3.3333333333333335,
      'Bazhyn' => 3.6666666666666665
    }

    expected_averages.each do |_surname, expected_avg|
      actual_avg = result.find { |avg| avg == expected_avg }
      assert_in_delta expected_avg, actual_avg, 0.0001
    end
  end

  def test_percentage_of_students_with_high_grades
    result = percentage_of_students_with_high_grades(@students_array, 4, 5)
    assert_equal 33.33333333333333, result
  end

  def test_best_subject
    result = best_subject(@students_array)
    assert_equal 'Computer_Science', result
  end

  def test_group_numbers_in_descending_order
    result = group_numbers_in_descending_order(@students_array)
    expected_result = [
      ['IPS-13', 5.0],
      ['IPS-12', 4.333333333333333],
      ['K-19', 4.0],
      ['K-12', 4.0],
      ['K-15', 4.0],
      ['K-13', 3.3333333333333335],
      ['IPS-11', 2.8333333333333335]
    ]

    expected_result.zip(result).each do |expected, actual|
      assert_equal expected, actual
    end
  end
end

class Student
  attr_accessor :surname, :group, :algebra_score, :geometry_score, :computer_science_score

  def initialize(surname, group, algebra_score, geometry_score, computer_science_score)
    @surname = surname
    @group = group
    @algebra_score = algebra_score
    @geometry_score = geometry_score
    @computer_science_score = computer_science_score
  end
end

students_array = [
  Student.new('Matviichuk', 'IPS-13', 5, 5, 5),
  Student.new('Petryk', 'IPS-13', 5, 5, 5),
  Student.new('Sidorov', 'IPS-11', 2, 2, 3),
  Student.new('Smirnov', 'K-15', 3, 4, 5),
  Student.new('Shalko', 'K-12', 4, 5, 5),
  Student.new('Petrov', 'K-19', 4, 4, 5),
  Student.new('Pysarenko', 'IPS-11', 5, 3, 2),
  Student.new('Avramenko', 'IPS-12', 4, 3, 4),
  Student.new('Kotik', 'K-12', 3, 4, 3),
  Student.new('Tishchenko', 'IPS-12', 5, 5, 5),
  Student.new('Mylostian', 'K-13', 4, 2, 4),
  Student.new('Bazhyn', 'K-19', 3, 5, 3)
]

def display_students_with_arrears(students)
  students_with_arrears = []
  students.each do |student|
    if student.algebra_score < 3 || student.geometry_score < 3 || student.computer_science_score < 3
      students_with_arrears << student.surname
    end
  end
  students_with_arrears
end

def quality_of_performance(students)
  students.map do |student|
    (student.algebra_score + student.geometry_score + student.computer_science_score) / 3.0
  end
  # average_scores.each_with_index { |avg, idx| puts "#{students[idx].surname}: #{avg}" }
end

def percentage_of_students_with_high_grades(students, grade1, grade2)
  total_students = students.length
  high_grades_count = students.count do |student|
    student.algebra_score >= grade1 && student.geometry_score >= grade2 && student.computer_science_score >= grade2
  end
  percentage = (high_grades_count.to_f / total_students) * 100

  # puts "Percentage of students with grades #{grade1} and #{grade2}: #{percentage}%"
end

def best_subject(students)
  subjects = %w[Algebra Geometry Computer_Science]
  subjects.max_by do |subject|
    average_score = students.map { |student| student.send("#{subject.downcase}_score") }.sum / students.length.to_f
    average_score
  end

  # puts "Best subject based on average score: #{best_subjects}"
end

def group_numbers_in_descending_order(students)
  group_average_scores = students.group_by(&:group).transform_values do |group_students|
    group_students.map do |student|
      (student.algebra_score + student.geometry_score + student.computer_science_score) / 3.0
    end.sum / group_students.length.to_f
  end

  group_average_scores.sort_by { |_group, avg| avg }.reverse
end

def console_output(students)
  puts "\nTask 1:"
  students_with_arrears = display_students_with_arrears(students)
  if students_with_arrears.empty?
    puts "No students with arrears (at least one subject with a grade of '2')"
  else
    puts 'Students with arrears:'
    puts students_with_arrears
  end

  puts "\nTask 2:"
  average_scores = quality_of_performance(students)
  puts 'Quality of Performance:'
  average_scores.each_with_index { |avg, idx| puts "#{students[idx].surname}: #{avg}" }

  puts "\nTask 3:"
  percentage = percentage_of_students_with_high_grades(students, 4, 5)
  puts "Percentage of students with grades 4 and 5: #{percentage}%"

  puts "\nTask 4:"
  subject = best_subject(students)
  puts "Best subject based on average score: #{subject}"

  puts "\nTask 5:"
  sorted_groups = group_numbers_in_descending_order(students)
  puts 'Group numbers in descending order of average scores:'
  sorted_groups.each { |group, avg| puts "Group #{group}: #{avg}" }
  puts "\n"
end

console_output(students_array)
