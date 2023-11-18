  # Variant 9
  
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
    Student.new("Matviichuk", "IPS-13", 5, 5, 5),
    Student.new("Petryk", "IPS-13", 5, 5, 5),
    Student.new("Sidorov", "IPS-11", 2, 2, 3),
    Student.new("Smirnov", "K-15", 3, 4, 5),
    Student.new("Shalko", "K-12", 4, 5, 5),
    Student.new("Petrov", "K-19", 4, 4, 5),
    Student.new("Pysarenko", "IPS-11", 5, 3, 2),
    Student.new("Avramenko", "IPS-12", 4, 3, 4),
    Student.new("Kotik", "K-12", 3, 4, 3),
    Student.new("Tishchenko", "IPS-12", 5, 5, 5),
    Student.new("Mylostian", "K-13", 4, 2, 4),
    Student.new("Bazhyn", "K-19", 3, 5, 3)
  ]

  def display_students_with_arrears(students)
    students_with_arrears = []

    students.each do |student|
      if student.algebra_score == 2 || student.geometry_score == 2 || student.computer_science_score == 2
        students_with_arrears << student.surname
      end
    end

    if students_with_arrears.empty?
      puts "No students with arrears (at least one subject with a grade of '2')"
    else
      puts "Students with arrears:"
      puts students_with_arrears
    end
  end

  def quality_of_performance(students)
    puts "Quality of Performance:"
    average_scores = students.map { |student| (student.algebra_score + student.geometry_score + student.computer_science_score) / 3.0 }
    average_scores.each_with_index { |avg, idx| puts "#{students[idx].surname}: #{avg}" }
  end

  def percentage_of_students_with_high_grades(students, grade1, grade2)
    total_students = students.length
    high_grades_count = students.count { |student| student.algebra_score >= grade1 && student.geometry_score >= grade2 && student.computer_science_score >= grade2 }
    percentage = (high_grades_count.to_f / total_students) * 100

    puts "Percentage of students with grades #{grade1} and #{grade2}: #{percentage}%"
  end

  def best_subject(students)
    subjects = %w[Algebra Geometry Computer_Science]
    best_subjects = subjects.max_by do |subject|
      average_score = students.map { |student| student.send("#{subject.downcase}_score") }.sum / students.length.to_f
      average_score
    end

    puts "Best subject based on average score: #{best_subjects}"
  end

  def group_numbers_in_descending_order(students)
    group_average_scores = students.group_by(&:group).transform_values do |group_students|
      group_students.map { |student| (student.algebra_score + student.geometry_score + student.computer_science_score) / 3.0 }.sum / group_students.length.to_f
    end

    sorted_groups = group_average_scores.sort_by { |_group, avg| avg }.reverse

    puts "Group numbers in descending order of average scores:"
    sorted_groups.each { |group, avg| puts "Group #{group}: #{avg}" }
  end


  
  puts "\nTask 1:"
  display_students_with_arrears(students_array)
  puts "\nTask 2:"
  quality_of_performance(students_array)
  puts "\nTask 3:"
  percentage_of_students_with_high_grades(students_array, 4, 5)
  puts "\nTask 4:"
  best_subject(students_array)
  puts "\nTask 5:"
  group_numbers_in_descending_order(students_array)
