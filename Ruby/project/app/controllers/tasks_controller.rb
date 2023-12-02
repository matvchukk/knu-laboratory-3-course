class TasksController < ApplicationController
  def students_with_arrears
    @students_with_arrears = display_students_with_arrears(Student.all)
  end

  def quality_of_performance
    @average_scores = display_quality_of_performance(Student.all)
  end

  def percentage_of_students_with_high_grades
    @percentage = percentage_of_students_with_high_grades(Student.all, 4, 5)
  end

  def best_subject
    @best_subject = best_subject(Student.all)
  end

  def group_numbers_in_descending_order
    @sorted_groups = group_numbers_in_descending_order(Student.all)
  end

  private

  def display_students_with_arrears(students)
    students_with_arrears = students.select do |student|
      student.algebra_score < 3 || student.geometry_score < 3 || student.computer_science_score < 3
    end
    students_with_arrears.map(&:surname)
  end

  def display_quality_of_performance(students)
    students.map do |student|
      (student.algebra_score + student.geometry_score + student.computer_science_score) / 3.0
    end
  end

  def percentage_of_students_with_high_grades(students, grade1, grade2)
    total_students = students.length
    high_grades_count = students.count do |student|
      student.algebra_score >= grade1 && student.geometry_score >= grade2 && student.computer_science_score >= grade2
    end
    (high_grades_count.to_f / total_students) * 100
  end

  def best_subject(students)
    subjects = %w[Algebra Geometry Computer_Science]
    subjects.max_by do |subject|
      average_score = students.map { |student| student.send("#{subject.downcase}_score") }.sum / students.length.to_f
      average_score
    end
  end

  def group_numbers_in_descending_order(students)
    group_average_scores = students.group_by(&:group).transform_values do |group_students|
      group_students.map do |student|
        (student.algebra_score + student.geometry_score + student.computer_science_score) / 3.0
      end.sum / group_students.length.to_f
    end

    group_average_scores.sort_by { |_group, avg| avg }.reverse
  end
end
