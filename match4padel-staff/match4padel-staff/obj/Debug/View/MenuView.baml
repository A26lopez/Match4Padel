   M S B A M L   `   `   `  ����L  Hmatch4padel-staff, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null?$clr-namespace:match4padel_staff.Viewmatch4padel_staff.View  5   	   %    match4padel_staff.View.MenuView   5   	   W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   6local$clr-namespace:match4padel_staff.View     5   	   _ [MaterialDesignThemes.Wpf, Version=1.0.1.0, Culture=neutral, PublicKeyToken=df2a72020bd7962aFmaterialDesign1http://materialdesigninxaml.net/winfx/xaml/themes  5   	      AppName  � Title#  [  5	   	   $	��720��5
   	   $
��1280��6    � WindowStartupLocation$ CenterScreen=�5   	    � 
ResizeMode$ CanMinimize=�5   	    � 	Resources 5        OpenMenuBar �� TargetProperty �� 
TargetName9  3System.Windows.Media.Animation.EasingDoubleKeyFrame O� KeyTime O� Value  CloseMenuBar��%�  &	       &	 �     �� 5   
   .��5      U� $ (FrameworkElement.Width)�6,   $ GridMenuBar��5   ,   .\�5        $ 0��6'   $ 40Q�63   6@     5      $ 0:0:0.3��6'   $	 256Q�69   6G   5      5      �� 5   
   .��5      U� $ (FrameworkElement.Width)�6,   $ GridMenuBar��5   ,   .\�5        $ 0��6'   $	 256Q�63   6A     5      $ 0:0:0.3��6'   $ 40Q�69   6F   5      5      5      .��5      �  � RowDefinitions 5   
   �� 5       $	��144 �6   �� 5!      $��* �6   .+�5%   
   �   BluePrimaryColor#���  6   $��0��6?   	 � ColumnDefinitions	 5&      �� 5'      $	��256 �6#   61   �� 5(      6%    5*      �� 5+      6"   .+�5-      �� 
 �� Orientation$
 
Horizontal=�6   $��1��5.      .+�5/      �� $��1��6   $��¡Bienvenido, ��50      
�������$��40(�52      $��Center=�53      �� 54      $��1��6   
�������$��40(�57      $��Center=�58      ��55      �� *!LoggedUser.AccountInfo.Username+58      �� 59      $��1��6   $��!��5:      
�������$��40(�5<      $��Center=�5=      5>      5?      � 5B   
   $��1��6   
������ 	 5D      �� 5E      $��40 �6#   60   �� 5F      $��* �6#   6/   .+�5H      �� -   5         ��Name$ main_content��5I      $��1��5H      ��5J      �� *CurrentView+5K      5M      � 5T   
   -   5         �Name$ GridMenuBar��5T      $��40��5U      $
��Left=�5V      $��2��5W      #���  5X       K� ZIndex$ 1��5Y       � Triggers 5[      :� 5\       :� RoutedEvent$ 
MouseEnter��6   .[�5]      �� #��[ 6&   5^      :� 5_      $ 
MouseLeave��6   .[�5`      �� #��[ 6&   5a      .+�5d      �� .+�5e      � $	��144��6   
�������.+�5f      ��   logo#��[ 6%   $��
20 0 20 14q�5g   %   $��Right=�5h   %   '  !MaterialDesignThemes.Wpf.PackIcon  5i         Kind$
 Menu=�6.   $��Bottom=�5j   .     menubar_icons_style#��[ 5k   .   $	��6.5q�5l   .   #��[ 5m   .   5n      �� 5r      .+�5u      ��   menubar_buttons_style#��[ 6   ��5v      �� *HomeCommand+.��5w      �� $
 
Horizontal=�6%   
������ .+�5y        $ ViewDashboard=�66   #��[ 5z   6   �� 5{      $��Inicio��6(     menubar_text_style#��[ 5|   (   6V   5}      5~      �� 5�      #��[ 6   ��5�      �� *ReservateCourtCommand+.��5�      �� $
 
Horizontal=�6%   .+�5�        $ Tennis=�66   #��[ 5�   6   �� 5�      $��Reservar pista��6(   #��[ 5�   (   6V   5�      5�      �� 5�      #��[ 6   ��5�      �� *MyReservationsCommand+.��5�      �� $
 
Horizontal=�6%   .+�5�        $ CalendarCheck=�66   #��[ 5�   6   �� 5�      $��Mis reservas��6(   #��[ 5�   (   6V   5�      5�      �� 5�      #��[ 6   ��5�      �� *CreateMatchCommand+.��5�      �� $
 
Horizontal=�6%   .+�5�        $ CalendarPlus=�66   #��[ 5�   6   �� 5�      $��Crear partido��6(   #��[ 5�   (   6V   5�      5�      �� 5�      #��[ 6   ��5�      �� *MyMatchesCommand+.��5�      �� $
 
Horizontal=�6%   .+�5�        $ 
TennisBall=�66   #��[ 5�   6   �� 5�      $��Mis partidos��6(   #��[ 5�   (   6V   5�      5�      �� 5�      #��[ 6   ��5�      �� *OpenMatchesCommand+.��5�      �� $
 
Horizontal=�6%   .+�5�        $ CalendarSearch=�66   #��[ 5�   6   �� 5�      $��Partidos abiertos��6(   #��[ 5�   (   6V   5�      5�      �� 5�      #��[ 6   .��5�      �� $
 
Horizontal=�6%   .+�5�        $ AccountCircle=�66   #��[ 5�   6   �� 5�      $��	Mi perfil��6(   #��[ 5�   (   6V   5�      5�      �� 5�      #��[ 6   .��5�      �� $
 
Horizontal=�6%   .+�5�        $	 Cog=�66   #��[ 5�   6   �� 5�      $��Configuración��6(   #��[ 5�   (   6V   5�      5�      5�      5�      5�      5�      5�      