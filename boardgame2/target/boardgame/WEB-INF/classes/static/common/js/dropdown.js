$(function(){
  $(".drop_menu	").parent().find(".drop_menu").hide(); //最初は非表示

  $(".drop_top").on("click", function() {
    $(this).parent().find(".drop_menu").slideToggle(0);
  });

  $(document).click(function() {$('.drop_menu').hide();});
  $('.drop_top').click(function() {event.stopPropagation();});
});