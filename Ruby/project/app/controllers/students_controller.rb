class StudentsController < ApplicationController
  http_basic_authenticate_with name: "11", password: "11", except: [:index, :show]

  def index
    @students = Student.all
  end

  def show
    @student = Student.find(params[:id])
  end

  def new
    @student = Student.new
  end

  def create
    @student = Student.new(student_params)

    if @student.save
      redirect_to @student
    else
      render :new, freshman_state: :unprocessable_entity
    end
  end

  def edit
    @student = Student.find(params[:id])
  end

  def update
    @student = Student.find(params[:id])

    if @student.update(student_params)
      redirect_to @student
    else
      render :edit, freshman_state: :unprocessable_entity
    end
  end

  def destroy
    @student = Student.find(params[:id])
    @student.destroy

    redirect_to root_path, freshman_state: :see_other
  end

  private
  def student_params
    params.require(:student).permit(:first_name, :middle_name, :last_name, :group, :algebra_score, :geometry_score, :computer_science_score, :freshman_state)
  end
end