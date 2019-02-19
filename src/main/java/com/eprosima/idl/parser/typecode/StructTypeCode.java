// Copyright 2016 Proyectos y Sistemas de Mantenimiento SL (eProsima).
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.eprosima.idl.parser.typecode;
import com.eprosima.idl.parser.tree.Inherits;
import com.eprosima.idl.context.Context;

import org.antlr.stringtemplate.StringTemplate;

import java.util.List;
import java.util.ArrayList;


public class StructTypeCode extends MemberedTypeCode implements Inherits
{
    public StructTypeCode(String scope, String name)
    {
        super(Kind.KIND_STRUCT, scope, name);
        superTypes_ = new ArrayList<StructTypeCode>();
    }

    @Override
    public String getTypeIdentifier()
    {
        return "EK_MINIMAL";
    }

    @Override
    public boolean isObjectType() { return true; }

    @Override
    public String getCppTypename()
    {
        StringTemplate st = getCppTypenameFromStringTemplate();
        st.setAttribute("name", getScopedname());
        return st.toString();
    }

    @Override
    public String getCTypename()
    {
        StringTemplate st = getCTypenameFromStringTemplate();
        st.setAttribute("name", getCScopedname());
        return st.toString();
    }

    @Override
    public String getJavaTypename()
    {
        StringTemplate st = getJavaTypenameFromStringTemplate();
        st.setAttribute("name", getJavaScopedname());
        return st.toString();
    }

    @Override
    public String getIdlTypename()
    {
        StringTemplate st = getIdlTypenameFromStringTemplate();
        st.setAttribute("name", getScopedname());
        return st.toString();
    }

    @Override
    public boolean isIsType_a(){
        return true;
    }

    /*public Pair<Integer, Integer> getMaxSerializedSize(int currentSize, int lastDataAligned)
      {
      List<Member> members = getMembers();
      int lcurrentSize = currentSize;
      int llastDataAligned = lastDataAligned;

      for(int count = 0; count < members.size(); ++count)
      {
      Pair<Integer, Integer> pair = members.get(count).getTypecode().getMaxSerializedSize(lcurrentSize, llastDataAligned);
      lcurrentSize = pair.first();
      llastDataAligned = pair.second();
      }

      return new Pair<Integer, Integer>(lcurrentSize, llastDataAligned);
      }

      public int getMaxSerializedSizeWithoutAlignment(int currentSize)
      {
      List<Member> members = getMembers();
      int lcurrentSize = currentSize;

      for(int count = 0; count < members.size(); ++count)
      {
      lcurrentSize = members.get(count).getTypecode().getMaxSerializedSizeWithoutAlignment(lcurrentSize);
      }

      return lcurrentSize;
      }

      public String getMaxSerializedSize()
      {
      Pair<Integer, Integer> pair = getMaxSerializedSize(0, 0);
      return pair.first().toString();
      }

      public String getMaxSerializedSizeWithoutAlignment()
      {
      return Integer.toString(getMaxSerializedSizeWithoutAlignment(0));
      }*/

    @Override
    public void addInheritance(Context ctx, TypeCode parent)
    {
        if (parent instanceof StructTypeCode)
        {
            superTypes_.add((StructTypeCode)parent);
        }
    }

    @Override
    public ArrayList<TypeCode> getInheritances()
    {
        ArrayList<TypeCode> result = new ArrayList<TypeCode>();
        for (StructTypeCode parent : superTypes_)
        {
            result.add(parent);
        }
        return result;
    }

    @Override
    public List<Member> getMembers()
    {
        List<Member> allMembers = new ArrayList<Member>();

        for (StructTypeCode p : superTypes_)
        {
            allMembers.addAll(p.getMembers());
        }
        allMembers.addAll(super.getMembers());
        return allMembers;
    }

    private ArrayList<StructTypeCode> superTypes_;
}
