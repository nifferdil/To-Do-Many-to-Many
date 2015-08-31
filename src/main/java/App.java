import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     model.put("template", "templates/index.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

   get("/tasks", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     List<Task> allCompleteTasks = Task.allCompletes();
     model.put("allCompleteTasks", allCompleteTasks);
     List<Task> tasks = Task.all();
     model.put("tasks", tasks);
     model.put("allCategories", Category.all());
     model.put("template", "templates/tasks.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

   get("/categories", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     List<Category> categories = Category.all();
     model.put("categories", categories);
     model.put("template", "templates/categories.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

    get("/tasks/:id/edit", (request,response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     int id = Integer.parseInt(request.params("id"));
     Task editTask = Task.find(id);
     model.put("editTask", editTask);
     List<Task> allCompleteTasks = Task.allCompletes();
     model.put("allCompleteTasks", allCompleteTasks);
     List<Task> tasks = Task.all();
     model.put("tasks", tasks);
     model.put("allCategories", Category.all());
     model.put("template", "templates/tasks.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

   get("/tasks/:id/complete", (request,response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    int id = Integer.parseInt(request.params("id"));
    Task completeTask = Task.find(id);
    completeTask.complete(completeTask.isTaskComplete());
    List<Task> allCompleteTasks = Task.allCompletes();
    model.put("allCompleteTasks", allCompleteTasks);
    List<Task> tasks = Task.all();
    model.put("tasks", tasks);



    model.put("allCategories", Category.all());
    model.put("template", "templates/tasks.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/tasks/:id/delete", (request,response) -> {
   HashMap<String, Object> model = new HashMap<String, Object>();
   int id = Integer.parseInt(request.params("id"));
   Task deleteTask = Task.find(id);

   deleteTask.delete(id);

   List<Task> allCompleteTasks = Task.allCompletes();
   model.put("allCompleteTasks", allCompleteTasks);
   List<Task> tasks = Task.all();
   model.put("tasks", tasks);

   model.put("allCategories", Category.all());
   model.put("template", "templates/tasks.vtl");
   return new ModelAndView(model, layout);
 }, new VelocityTemplateEngine());

   post("/tasks/:id/edit", (request,response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    int id = Integer.parseInt(request.params("id"));
    Task editTask = Task.find(id);
    String description = request.queryParams("description");
    String duedate = request.queryParams("duedate");
    editTask.update(description, duedate);
    List<Task> allCompleteTasks = Task.allCompletes();
    model.put("allCompleteTasks", allCompleteTasks);
    List<Task> tasks = Task.all();
    model.put("tasks", tasks);
    model.put("allCategories", Category.all());
    model.put("template", "templates/tasks.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());


    get("/tasks/:id", (request,response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    int id = Integer.parseInt(request.params("id"));
    Task task = Task.find(id);
    model.put("task", task);
    model.put("allCategories", Category.all());
    model.put("template", "templates/task.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/categories/:id", (request,response) ->{
    HashMap<String, Object> model = new HashMap<String, Object>();
    int id = Integer.parseInt(request.params("id"));
    Category category = Category.find(id);
    model.put("category", category);
    model.put("allTasks", Task.all());
    model.put("template", "templates/category.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/tasks", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    String description = request.queryParams("description");
    String duedate = request.queryParams("duedate");
    Task newTask = new Task(description, false, duedate);
    newTask.save();
    response.redirect("/tasks");
    return null;
  });

  post("/categories", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    String name = request.queryParams("name");
    Category newCategory = new Category(name);
    newCategory.save();
    response.redirect("/categories");
    return null;
  });

  post("/add_tasks", (request, response) -> {
  int taskId = Integer.parseInt(request.queryParams("task_id"));
  int categoryId = Integer.parseInt(request.queryParams("category_id"));
  Category category = Category.find(categoryId);
  Task task = Task.find(taskId);
  category.addTask(task);
  response.redirect("/categories/" + categoryId);
  return null;
});

  post("/add_categories", (request, response) -> {
    int taskId = Integer.parseInt(request.queryParams("task_id"));
    int categoryId = Integer.parseInt(request.queryParams("category_id"));
    Category category = Category.find(categoryId);
    Task task = Task.find(taskId);
    task.addCategory(category);
    response.redirect("/tasks/" + taskId);
    return null;
  });



 }
}
