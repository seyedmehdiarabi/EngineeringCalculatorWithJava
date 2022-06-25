import java.util.Stack;

/**
 *
 * Seyed Mahdi Arabi
 */
public class Calculate {
    
    private static Stack stack=new Stack();
    
    public String Evaluation(String infix,boolean IsDegree)
    {
        String[] postfix=InfixToPostfix(infix);
        for(int i=0;i<postfix.length;i++)
        {
            if(postfix[i]==null) break;
            String c=postfix[i];
            if(IsOperator(c))
            {
                if(c.equals("-") || c.equals("+") || c.equals("*") || c.equals("/") || c.equals("^"))
                {
                    double op2=Double.parseDouble(stack.pop().toString());
                    double op1=Double.parseDouble(stack.pop().toString());
                    double result=0;
                    switch(c)
                    {
                        case "+" :
                            result=op1+op2;
                            stack.push(result);
                            break;
                            case "-" :
                            result=op1-op2;
                            stack.push(result);
                            break;
                            case "*" :
                            result=op1*op2;
                            stack.push(result);
                            break;
                            case "/" :
                            result=op1/op2;
                            stack.push(result);
                            break;
                            case "+^" :
                            result=Math.pow(op1, op2);
                            stack.push(result);
                            break;
                    }
                }
                else
                {
                     double op1=Double.parseDouble(stack.pop().toString());
                     double result=0;
                     switch(c)
                     {
                          case "Tan" :
                            result=(IsDegree)?Math.tan(op1*Math.PI/180.0):Math.tan(op1);
                            stack.push(result);
                            break;
                            case "Cos" :
                            result=(IsDegree)?Math.cos(op1*Math.PI/180.0):Math.cos(op1);
                            stack.push(result);
                            break;
                            case "Sin" :
                            result=(IsDegree)?Math.sin(op1*Math.PI/180.0):Math.sin(op1);
                            stack.push(result);
                            break;
                            case "Log" :
                            result=Math.log10(op1);
                            stack.push(result);
                            break;
                            case "Ln" :
                            result=Math.log(op1);
                            stack.push(result);
                            break;
                            case "Sqr" :
                            result=Math.sqrt(op1);
                            stack.push(result);
                            break;
                     }
                }
              
            }
            else
            {
                stack.push(c);
            }
        }
        return stack.pop().toString();
    }
    private boolean IsOperator(String input)
    {
        if(Character.isDigit(input.charAt(0))) return false;
        else return true;
    }
    private static int Priority(String c)
    {
        if(c.equals("+") || c.equals("-")) return 0;
        else if(c.equals("*") || c.equals("/")) return 1;
        else if(c.equals("^")) return 2;
        else if ((c.equals("Log")) || (c.equals("Sin")) ||(c.equals("Ln")) ||(c.equals("Sqr"))|| (c.equals("Cos")) || ((c.equals("Tan")))) return 3;
        return -1;
    }
    private static String[] InfixToPostfix(String Input)
    {
        String[] postfix=new String[Input.length()];
        int k=0;
        for(int i=0;i<Input.length();i++)
        {
           postfix[k]="";
           String c=String.valueOf(Input.charAt(i));
           if(Character.isDigit(c.charAt(0)))
           {
               while(Character.isDigit(Input.charAt(i))|| Input.charAt(i)=='.')
               {
                    postfix[k]=postfix[k]+String.valueOf(Input.charAt(i));
                    i++;
                    if(i==Input.length())break;
                    
               }
               i--;
               k++;
           }
           else if(c.equals("("))
           {
               stack.push(c);
           }
           else if(c.equals(")"))
           {
               while(!String.valueOf(stack.peek()).equals("("))
               {
                   postfix[k]=String.valueOf(stack.pop());
                   k++;
               }
               stack.pop();
           }
           else if(c.equals("-") || c.equals("+") || c.equals("*") || c.equals("/") || c.equals("^") || c.equals("S") || c.equals("C") || c.equals("T") || c.equals("L"))
           {
               //Sin(30)+45
               String operator="";
               int count=0;
               while(Character.isLetter(Input.charAt(i)))
               {
                   operator+=String.valueOf(Input.charAt(i));
                   i++;
                   count++;
               }
               if(count>0)
               {
                   stack.push(operator);
                   i--;
               }
               else if(count==0)
               {
                   while(stack.size()>0 && !String.valueOf(stack.peek()).equals("(") && Priority(c)<=Priority(String.valueOf(stack.peek())))
                   {
                       postfix[k]=String.valueOf(stack.pop());
                       k++;
                   }
                   stack.push(c);
                   
               }
           }
        }
        while(stack.size()>0)
        {
             postfix[k]=String.valueOf(stack.pop());
             k++;
        }
        return postfix;
    }
}
