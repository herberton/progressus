using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Utility.GenericException
{
    public class Exception<T> : Exception where T : Exception
    {
        public T Exception { get; set; }

        public Exception(T exception) 
            : base(exception.Message, exception) 
        {
            this.Exception = exception;
        }
    }
}
